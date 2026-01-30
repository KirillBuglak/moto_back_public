package kb.moto.util

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.context.MessageSource
import java.util.Locale

class MotoUtilsTest {

    @Test
    fun `getMessage uses provided locale or defaults`() {
        val ms = mock<MessageSource>()
        whenever(ms.getMessage("code", null, Locale.ENGLISH)).thenReturn("en")
        whenever(ms.getMessage("code", null, Locale.FRENCH)).thenReturn("fr")

        val utils = MotoUtils()
        // set private field via reflection or change MotoUtils to constructor injection (recommended)
        val f = MotoUtils::class.java.getDeclaredField("messageSource")
        f.isAccessible = true
        f.set(utils, ms)

        assertEquals("fr", utils.getMessage("code", null, Locale.FRENCH))
        assertEquals("en", utils.getMessage("code", null, null))
    }
}