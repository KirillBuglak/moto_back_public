package kb.moto.service

import kb.moto.model.kafka.EmailKafkaMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.StringWriter

@Service
class EmailService @Autowired constructor(
    private val mailSender: JavaMailSender,
    private val templateService: MessageTemplateService
){
    fun sendSimpleEmail(kafkaMessage: EmailKafkaMessage) =
        formEmailBody(kafkaMessage.templateName, kafkaMessage.templateValues)
            .doOnNext {
                val message = SimpleMailMessage()
                message.setTo(kafkaMessage.to)
                message.subject = kafkaMessage.subject
                message.text = it
                mailSender.send(message)
                System.err.println("sent - $message")
            }
            .map { Pair( kafkaMessage.to, it) }
    private fun formEmailBody(templateName: String, templateValues: Map<String, Any>): Mono<String>
        = templateService.find(templateName)
            .map {
                val stringWriter = StringWriter()
                val context = VelocityContext()
                templateValues.forEach(context::put)
                Velocity.evaluate(context, stringWriter, "VelocityTemplate", it.text)
                return@map stringWriter.toString()
            }

    suspend fun sendSimpleEmailCor(to: String, subject: String, body: String) {
        withContext(Dispatchers.IO) {
            val message = SimpleMailMessage().apply {
                setTo(to)
                this.subject = subject
                this.text = body
            }
            mailSender.send(message)
        }
    }
}