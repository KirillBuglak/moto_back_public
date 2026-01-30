package kb.moto.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class MotoUtils  {
        @Autowired
        private lateinit var messageSource: MessageSource

        fun getMessage(code: String, array: Array<Any?>?, locale: Locale?) =
            messageSource.getMessage(
                code, array,
                locale ?: Locale.ENGLISH
            )
}