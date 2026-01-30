package kb.moto.service.abstr

import kb.moto.model.ids.StringId
import kb.moto.util.MotoUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
abstract class AbstractMongoCRUDService<T: StringId>@Autowired constructor(
    private val repository: ReactiveMongoRepository<T, String>) {

    @Autowired
    private lateinit var utils: MotoUtils //todo use this instead of 'Oops' as error messages

    fun create(v: T): Mono<T> = repository.insert(v)

    fun get(id: String): Mono<T> = repository.findById(id)
        .switchIfEmpty { Mono.error(RuntimeException("Oops")) }

    fun getAll(): Flux<T> = repository.findAll()

    fun update(v: T): Mono<T> {
        return repository.existsById(v.id!!)
            .flatMap {
                when {
                    it -> repository.save(v)
                    else -> Mono.error(RuntimeException("Oops"))
                }
            }
    }

    fun delete(id: String): Mono<Any> {
        return repository.existsById(id)
            .flatMap {
                when {
                    it -> repository.deleteById(id)
                    else -> Mono.error(RuntimeException("Oops"))
                }
            }
    }
}