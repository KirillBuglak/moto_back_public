package kb.moto.service

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kb.moto.model.MessageTemplate
import kb.moto.model.kafka.EmailKafkaMessage
import org.junit.jupiter.api.Test
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class EmailServiceTest {

    @Test
    fun `sendSimpleEmail renders template and sends mail`() {
        // Mocks
        val mailSender = mockk<JavaMailSender>()
        val templateService = mockk<MessageTemplateService>()
        // Sample template: Velocity uses $name
        val template = MessageTemplate(name = "greet", text = "Hello \${name} Welcome.")
        every { templateService.find("greet") } returns Mono.just(template)
        every { mailSender.send(any<SimpleMailMessage>()) } just runs

        // Create service and inject mocked templateService (private autowired field)
        val emailService = EmailService(mailSender, templateService)
        val f = EmailService::class.java.getDeclaredField("templateService")
        f.isAccessible = true

        val kafkaMessage = EmailKafkaMessage(
            to = "user@example.com",
            subject = "Hi",
            templateName = "greet",
            templateValues = mapOf("name" to "John")
        )

        val resultMono = emailService.sendSimpleEmail(kafkaMessage)

        StepVerifier.create(resultMono)
            .expectNextMatches { pair ->
                pair.first == "user@example.com" && pair.second.contains("Hello John")
            }
            .verifyComplete()

        // Verify mailSender.send received a message with expected fields
        verify {
            mailSender.send(match<SimpleMailMessage> { msg ->
                msg.to?.contentEquals(arrayOf("user@example.com")) == true &&
                        msg.subject == "Hi" &&
                        msg.text!!.contains("Hello John")
            })
        }
    }
}