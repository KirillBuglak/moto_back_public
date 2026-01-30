package kb.moto.repo

import kb.moto.model.MessageTemplate
import kb.moto.model.SentHistory
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository("messageTemplateRepo")
interface MessageTemplateRepo: R2dbcRepository<MessageTemplate, Any> {
    fun findByName(name: String): Mono<MessageTemplate>
    fun deleteByName(name: String): Mono<Void>
    fun existsByName(name: String): Mono<Boolean>
}

@Repository("sentHistoryRepo")
interface SentHistoryRepo: R2dbcRepository<SentHistory, Any>