package kb.moto.config

import kb.moto.jwt.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class ServerAuthenticationConverterImpl: ServerAuthenticationConverter {

    @Autowired
    private lateinit var utils: JwtUtil

    override fun convert(exchange: ServerWebExchange): Mono<Authentication>? {
        val result = Mono.just(exchange)
            .flatMap { utils.getJwtCookie(it)?.value
                ?.let { token ->
                    if (utils.validateToken(token)) {
                        return@flatMap Mono.just(utils.getUsernamePasswordAuthenticationToken(token) as Authentication)
                    }
                }
                return@flatMap Mono.empty()
            }

        return result
    }
}