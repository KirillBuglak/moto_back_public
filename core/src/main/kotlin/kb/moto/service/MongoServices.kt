package kb.moto.service

import kb.moto.exceptions.UserContactsNotFound
import kb.moto.model.auth.UserContacts
import kb.moto.model.ids.StringId
import kb.moto.repo.UserContactsRepository
import kb.moto.service.abstr.AbstractMongoCRUDService
import kb.moto.util.MotoUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service("authorityService")
class AuthorityService<T: StringId> @Autowired constructor(authorityRepository : ReactiveMongoRepository<T, String>) : AbstractMongoCRUDService<T>(authorityRepository)

@Service("roleService")
class RoleService<T: StringId> @Autowired constructor(roleRepository : ReactiveMongoRepository<T, String>) : AbstractMongoCRUDService<T>(roleRepository)

@Service("userContactsService")
class UserContactsService @Autowired constructor(val utils: MotoUtils, val userContactsRepository : UserContactsRepository) : AbstractMongoCRUDService<UserContacts>(userContactsRepository) {

    @Cacheable(value = ["userContacts"], key = "#root.target.class.simpleName + '/' + #userName")
    fun getContactsByUserName(userName: String): Mono<UserContacts> = userContactsRepository.findByUsername(userName)
        .switchIfEmpty { noSuchContacts(userName) }

    private fun noSuchContacts(userName: String): Mono<UserContacts> =
        Mono.error(
            UserContactsNotFound(
                utils
                    .getMessage("contacts.not.found", arrayOf(userName), null)
            )
        )
}
