package kb.moto.controller

import kb.moto.controller.abstr.AbstractMongoCRUDController
import kb.moto.model.auth.Authority
import kb.moto.model.auth.Role
import kb.moto.model.auth.UserContacts
import kb.moto.service.UserContactsService
import kb.moto.service.abstr.AbstractMongoCRUDService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController("authorityController")
@RequestMapping("admin/authorities")
class AuthorityController @Autowired constructor(authorityService: AbstractMongoCRUDService<Authority>) : AbstractMongoCRUDController<Authority>(authorityService)

@RestController("roleController")
@RequestMapping("admin/roles")
class RoleController @Autowired constructor(roleService: AbstractMongoCRUDService<Role>) : AbstractMongoCRUDController<Role>(roleService)

@RestController("userContactsController")
@RequestMapping("/contacts")
class UserContactsController @Autowired constructor(val userContactsService: UserContactsService) : AbstractMongoCRUDController<UserContacts>(userContactsService) {
    @GetMapping("/byUserName/{userName}")
    fun getByUserName(@PathVariable userName: String): Mono<UserContacts> = userContactsService.getContactsByUserName(userName)
}