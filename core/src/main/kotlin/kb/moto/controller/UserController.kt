package kb.moto.controller

import kb.moto.model.auth.User
import kb.moto.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("admin/users")
class UserController @Autowired constructor(
    private val userService: UserService
) {

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userName}")
    fun get(@PathVariable userName: String): Mono<User> = userService.getUserByUserName(userName)

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/full")
    fun getAllFull(): Flux<User> = userService.getAll()

    @GetMapping
    fun getAllList(): Flux<Map<String, String?>?> = userService.getAllList()

//    @PreAuthorize("hasRole('ULTIMATE')")
    @PostMapping
    fun create(@RequestBody user: User): Mono<User> = userService.create(user)

//    @PreAuthorize("hasRole('ULTIMATE')")
    @PatchMapping
    fun update(@RequestBody user: User): Mono<User> = userService.update(user)

//    @PreAuthorize("hasRole('ULTIMATE')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): Mono<Void?> = userService.delete(id)
}