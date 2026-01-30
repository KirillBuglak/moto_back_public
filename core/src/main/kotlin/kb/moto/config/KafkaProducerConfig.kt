package kb.moto.config

import kb.moto.model.kafka.EmailKafkaMessage
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions


@Configuration
class KafkaProducerConfig {

    @Bean
    fun reactiveKafkaProducerTemplate(properties: KafkaProperties): ReactiveKafkaProducerTemplate<String, EmailKafkaMessage> {
        val props = properties.buildProducerProperties(null)
        return ReactiveKafkaProducerTemplate(SenderOptions.create(props))
    }
}