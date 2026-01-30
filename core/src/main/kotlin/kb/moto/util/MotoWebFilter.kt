package kb.moto.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class MotoWebFilter @Autowired constructor(val crsl: MotoLocaleContextResolver): WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        System.err.println("locales - ${exchange.request.headers.acceptLanguageAsLocales}")
        crsl.resolveLocaleContext(exchange)
        return chain.filter(exchange)
    }
}