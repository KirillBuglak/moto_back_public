package kb.moto.service.abstr

import kb.moto.model.ids.MotoId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
abstract class AbstractPostgresService<T: MotoId<Any>> @Autowired constructor(
    private val repo: R2dbcRepository<T, Any>
) {

    open fun getAll() = repo.findAll()

    fun getById(id: Long, exception: RuntimeException) = repo.findById(id)
        .switchIfEmpty { Mono.error(exception) }

    fun create(record: T) = repo.save(record)

    fun update(record: T, exception: RuntimeException) = repo.existsById(record.id!!) // TODO: may not need exist check due to two repo calls, use databaseClient.sql
        .flatMap {
            when {
                it -> repo.save(record)
                else -> Mono.error(exception)
            }
        }

    fun delete(id: Long, exception: RuntimeException) = repo.existsById(id) // TODO: may not need exist check due to two repo calls, use databaseClient.sql
        .flatMap {
            when {
                it -> repo.deleteById(id)
                else -> Mono.error(exception)
            }
        }
}