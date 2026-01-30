package kb.moto.config

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono

class ReactiveAuthenticationManagerImpl: ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.just(authentication)
    }
}