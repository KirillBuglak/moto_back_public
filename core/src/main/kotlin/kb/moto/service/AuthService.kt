package kb.moto.service

import kb.moto.exceptions.WrongPassword
import kb.moto.jwt.JwtUtil
import kb.moto.model.auth.AuthRequest
import kb.moto.model.auth.User
import kb.moto.util.MotoUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseCookie
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class AuthService @Autowired constructor(
    val jwtUtil: JwtUtil,
    val utils: MotoUtils,
    val userService: UserService,
    val passwordEncoder: PasswordEncoder
) {

    fun getToken(request: AuthRequest, exchange: ServerWebExchange): Mono<String> {
        return userService.getUserByUserName(request.userName)
            .flatMap { user -> validatePassword(request.password, user) }
            .doOnNext { token -> addTokenCookie(exchange, token, Duration.ofMillis(jwtUtil.TOKEN_VALIDITY_TIME!!)) }
            .thenReturn("Authenticated")
    }

    fun invalidateToken(exchange: ServerWebExchange): Mono<String> {
        //Don't really need mono(Dispatchers.Default) here - not a costly operation
        return mono(Dispatchers.Default) {
            addTokenCookie(exchange, "", Duration.ofSeconds(0))
        }.thenReturn("Token invalidated")
    }

    private fun validatePassword(password: String, user: User): Mono<String> {
        return if (passwordEncoder.matches(password, user.getPass())) {
            Mono.just(jwtUtil.generateToken(user))
        } else {
            wrongPassword()
        }
    }

    private fun wrongPassword(): Mono<String> =
        Mono.error(
            WrongPassword(utils.getMessage("password.wrong", null, null))
        )

    private fun addTokenCookie(exchange: ServerWebExchange, token: String, duration: Duration) {
        exchange.response.addCookie(
            ResponseCookie
                .from("JWT", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None") // Allow cross-site (requires Secure)
//                .domain("localhost")
                .path("/")
                .maxAge(duration)
                .build()
        )
    }

    fun getAuthoritiesFromToken(exchange: ServerWebExchange) =
        Mono.just(exchange)
            .map { jwtUtil.getJwtCookie(it)?.value
            ?.let { token ->
                if (jwtUtil.validateToken(token)) {
                    return@map jwtUtil.getAuthoritiesFromToken(token)
                }
                return@map null
            }
        }
}