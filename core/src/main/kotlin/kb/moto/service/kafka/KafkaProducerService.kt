package kb.moto.service.kafka

import kb.moto.model.kafka.EmailKafkaMessage
import org.apache.kafka.shaded.com.google.protobuf.Timestamp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service


@Service
class KafkaProducerService  {
    @Value("\${spring.kafka.template.default-topic}")
    private lateinit var topic: String

    @Autowired
    private lateinit var producer: ReactiveKafkaProducerTemplate<String, EmailKafkaMessage>

    fun sendMessage(emailKafkaMessage: EmailKafkaMessage) =
        producer.send(topic, Timestamp.SECONDS_FIELD_NUMBER.toString(), emailKafkaMessage)
            .doOnEach { println(it) }
}
