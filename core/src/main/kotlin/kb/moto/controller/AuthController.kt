package kb.moto.controller

import kb.moto.model.auth.AuthRequest
import kb.moto.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
class AuthController {
    @Autowired
    private lateinit var service: AuthService

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest, exchange: ServerWebExchange): Mono<String> = service.getToken(request, exchange)

    @GetMapping("/authorize")
    fun auth(exchange: ServerWebExchange) = service.getAuthoritiesFromToken(exchange)

    @PostMapping("/logout")
    fun logout(exchange: ServerWebExchange): Mono<String> = service.invalidateToken(exchange)
}