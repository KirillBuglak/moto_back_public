package kb.moto.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MessageTemplateSerializationTest {

    private val mapper = ObjectMapper().registerModule(KotlinModule.Builder().build())

    @Test
    fun `message template serializes and deserializes correctly`() {
        val template = MessageTemplate(name = "welcome", text = "Hello, %s!")
        val json = mapper.writeValueAsString(template)
        val deserialized: MessageTemplate = mapper.readValue(json)

        assertEquals(template.name, deserialized.name)
        assertEquals(template.text, deserialized.text)
    }
}