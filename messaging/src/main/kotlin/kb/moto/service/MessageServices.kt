package kb.moto.service

import kb.moto.model.MessageTemplate
import kb.moto.model.ids.MotoId
import kb.moto.repo.MessageTemplateRepo
import kb.moto.service.abstr.AbstractPostgresService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service("sentHistoryService")
class SentHistoryService<T: MotoId<Any>> @Autowired constructor(sentHistoryRepo: R2dbcRepository<T, Any>
): AbstractPostgresService<T>(sentHistoryRepo)

@Service("messageTemplateService")
class MessageTemplateService @Autowired constructor(val messageTemplateRepo: MessageTemplateRepo)
    : AbstractPostgresService<MessageTemplate>(messageTemplateRepo) {
    fun find(name: String) = messageTemplateRepo.findByName(name)
        .switchIfEmpty {
            noSuchTemplate()
        }

    private fun noSuchTemplate(): Mono<MessageTemplate> =
        Mono.error(RuntimeException("no such template"))
}