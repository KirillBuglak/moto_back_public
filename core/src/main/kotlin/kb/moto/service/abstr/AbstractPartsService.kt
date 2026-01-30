package kb.moto.service.abstr

import kb.moto.exceptions.PartNotFound
import kb.moto.model.ids.MotoId
import kb.moto.model.kafka.EmailKafkaMessage
import kb.moto.service.UserContactsService
import kb.moto.service.kafka.KafkaProducerService
import kb.moto.util.MotoUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
abstract class AbstractPartsService<T: MotoId<Any>> @Autowired constructor(
    repo: R2dbcRepository<T, Any>
): AbstractPostgresService<T>(repo) {

    @Autowired
    private lateinit var utils: MotoUtils
    @Autowired
    private lateinit var messageService: KafkaProducerService

    @Autowired
    private lateinit var userContactsService: UserContactsService

    @Cacheable(value = ["parts"], key = "#root.target.class.simpleName")
    override fun getAll() = super.getAll()

    @Cacheable(value = ["parts"], key = "#root.target.class.simpleName + '/' + #id")
    fun getById(id: Long, locale: Locale?) = super.getById(id,partNotFound(id, locale))

    @CachePut(value = ["parts"], key = "#root.target.class.simpleName + '/' + #part.id")
    @CacheEvict(value = ["parts"], key = "#root.target.class.simpleName")
    fun create(part: T, userName: String, locale: Locale?) = super.create(part)
        .doOnNext { created -> // TODO: Can be done with a flatMap as below
            userContactsService.getContactsByUserName(userName)
                .flatMap { userContacts ->
                    messageService.sendMessage(EmailKafkaMessage(
                        userContacts.email,
                        "Part created",
                        "part_created",
                        mapOf("id" to created.id!!, "table" to created.javaClass.simpleName)))
                }
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe()
        }
// TODO:  Alternative to doOnNext option above
//        .flatMap { created ->
//            userContactsService.getContactsByUserName(userName)
//                .flatMap { userContacts ->
//                    messageService.sendMessage(EmailKafkaMessage(
//                        userContacts.email,
//                        "Part created",
//                        "part_created",
//                        mapOf("id" to created.id!!, "table" to created.javaClass.simpleName)))
//                }
//                // don't fail the creation if messaging fails
//                .onErrorResume { Mono.empty() }
//                // return the created entity down the chain
//                .thenReturn(created)
//        }

    @CachePut(value = ["parts"], key = "#root.target.class.simpleName + '/' + #part.id")
    @CacheEvict(value = ["parts"], key = "#root.target.class.simpleName")
    fun update(part: T, locale: Locale?) = super.update(part, partNotFound(part.id, locale))

    @Caching(
        evict = [
            CacheEvict(value = ["parts"], key = "#root.target.class.simpleName"),
            CacheEvict(value = ["parts"], key = "#root.target.class.simpleName + '/' + #id")
        ]
    )
    fun delete(id: Long, locale: Locale?) = super.delete(id, partNotFound(id, locale))
        .then(
            Mono.just(
                ResponseEntity.ok(utils.getMessage("part.deleted", arrayOf(id), locale))
            )
        )

    private fun partNotFound(id: Any?, locale: Locale?): PartNotFound =
        PartNotFound(utils.getMessage("part.not.found", arrayOf(id), locale))
}
