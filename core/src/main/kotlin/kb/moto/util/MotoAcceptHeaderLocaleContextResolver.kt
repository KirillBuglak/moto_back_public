package kb.moto.util

import org.springframework.stereotype.Component
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver

@Component
class MotoAcceptHeaderLocaleContextResolver: AcceptHeaderLocaleContextResolver() {
}