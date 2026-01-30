package kb.moto.util

import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.SimpleLocaleContext
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.i18n.LocaleContextResolver
import java.util.*

@Component
class MotoLocaleContextResolver: LocaleContextResolver {
    override fun resolveLocaleContext(exchange: ServerWebExchange): LocaleContext {
        val language = exchange.request.headers.getFirst("Accept-Language")
        return SimpleLocaleContext(Locale.forLanguageTag(language?:"en"))
    }

    override fun setLocaleContext(exchange: ServerWebExchange, localeContext: LocaleContext?) {
        TODO("Not yet implemented")
    }
}