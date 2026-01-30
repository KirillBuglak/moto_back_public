package kb.moto.service

import kb.moto.exceptions.UserNotFound
import kb.moto.model.auth.User
import kb.moto.repo.UserRepository
import kb.moto.util.MotoUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class UserService @Autowired constructor(
    val passwordEncoder: PasswordEncoder,
    val userRepository: UserRepository,
    val utils: MotoUtils
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @CachePut(value = ["users"], key = "#result.id")
    @CacheEvict(value = ["users"], key = "#root.target.class.simpleName")
    fun create(user: User): Mono<User> = Mono.just(user)
        .map { it.copy(password = passwordEncoder.encode(it.getPass())) }
        .flatMap { userRepository.insert(it) }

    @CachePut(value = ["users"], key = "#result.id")
    fun getUserByUserName(userName: String): Mono<User> = userRepository.findByUsername(userName)
        .switchIfEmpty { noSuchUser(userName) }

    @Cacheable(value = ["users"], key = "#root.target.class.simpleName + '/' + full")
    fun getAll(): Flux<User> = userRepository.findAll()

    @Cacheable(value = ["users"], key = "#root.target.class.simpleName")
    fun getAllList(): Flux<Map<String, String?>?> {
        return getAll().map { mapOf("id" to it.id, "username" to it.username) }
    }

    @CachePut(value = ["users"], key = "#updatedUser.id")
    @CacheEvict(value = ["users"], key = "#root.target.class.simpleName")
    fun update(updatedUser: User): Mono<User> {
        return userRepository.findById(updatedUser.id!!)
            .flatMap { it ->
                updatedUser.id = it.id
                userRepository.save(updatedUser)
            }.switchIfEmpty(noSuchUser(updatedUser.username))
    }

    @Caching(
        evict = [
            CacheEvict(value = ["users"], key = "#id"),
            CacheEvict(value = ["users"], key = "#root.target.class.simpleName")
        ]
    )
    fun delete(id: String): Mono<Void?> {
        return userRepository.existsById(id)
            .flatMap {
                when {
                    it -> userRepository.deleteById(id)
                    else -> noSuchUserWithId(id)
                }
            }
    }

    private fun noSuchUser(userName: String): Mono<User> =
        Mono.error(
            UserNotFound(
                utils
                    .getMessage("user.not.found", arrayOf(userName), null)
            )
        )

    private fun noSuchUserWithId(id: String): Mono<Void?> =
        Mono.error(
            UserNotFound(
                utils
                    .getMessage("user.not.found.id", arrayOf(id), null)
            )
        )
}