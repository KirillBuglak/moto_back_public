package kb.moto.controller.abstr

import kb.moto.model.ids.MotoId
import kb.moto.service.abstr.AbstractPartsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.util.*

@Component
abstract class AbstractPartsController<T: MotoId<Any>> @Autowired constructor(private val service: AbstractPartsService<T>) {

    @GetMapping
    fun getAll() = service.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long, locale: Locale?) = service.getById(id, locale)

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun create(@RequestBody body: T, locale: Locale?, @AuthenticationPrincipal principal: Authentication) = service.create(body, principal.name, locale)

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long, locale: Locale?) = service.delete(id, locale)

    @PatchMapping
    fun update(@RequestBody body: T, locale: Locale?) = service.update(body, locale)
}
