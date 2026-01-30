package kb.moto.config

import kb.moto.model.kafka.EmailKafkaMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.kafka.receiver.ReceiverOptions
import java.util.*


@Configuration
class KafkaConsumerConfig {

    @Bean
    fun kafkaReceiverOptions(
        @Value("\${spring.kafka.template.default-topic}") topic: String,
        kafkaProperties: KafkaProperties
    ): ReceiverOptions<String, EmailKafkaMessage> {
        val basicReceiverOptions: ReceiverOptions<String, EmailKafkaMessage> =
            ReceiverOptions.create(kafkaProperties.buildConsumerProperties(null))
        return basicReceiverOptions.subscription(Collections.singletonList(topic))
    }

    @Bean
    fun reactiveKafkaConsumerTemplate(kafkaReceiverOptions: ReceiverOptions<String, EmailKafkaMessage>): ReactiveKafkaConsumerTemplate<String, EmailKafkaMessage> {
        return ReactiveKafkaConsumerTemplate(kafkaReceiverOptions)
    }
}