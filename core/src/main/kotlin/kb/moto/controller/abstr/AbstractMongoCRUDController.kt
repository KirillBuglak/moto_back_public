package kb.moto.controller.abstr

import kb.moto.model.ids.StringId
import kb.moto.service.abstr.AbstractMongoCRUDService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
abstract class AbstractMongoCRUDController<T: StringId> @Autowired constructor(
    private val service: AbstractMongoCRUDService<T>
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: String): Mono<T> = service.get(id)

    @GetMapping
    fun getAll(): Flux<T> = service.getAll()

    @PostMapping
    fun create(@RequestBody v: T): Mono<T> = service.create(v)

    @PatchMapping
    fun update(@RequestBody v: T): Mono<T> = service.update(v)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): Mono<Any> = service.delete(id)
}