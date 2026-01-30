package kb.moto.repo

import kb.moto.model.auth.Authority
import kb.moto.model.auth.Role
import kb.moto.model.auth.User
import kb.moto.model.auth.UserContacts
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository: ReactiveMongoRepository<User, String> {
    fun findByUsername(userName: String): Mono<User>
    fun deleteByUsername(userName: String): Mono<Void>
    fun existsByUsername(userName: String): Mono<Boolean>
}

@Repository
interface AuthorityRepository: ReactiveMongoRepository<Authority, String>

@Repository
interface RoleRepository: ReactiveMongoRepository<Role, String>

@Repository
interface UserContactsRepository: ReactiveMongoRepository<UserContacts, String> {
    fun findByUsername(userName: String): Mono<UserContacts>
    fun deleteByUsername(userName: String): Mono<Void>
    fun existsByUsername(userName: String): Mono<Boolean>
}
