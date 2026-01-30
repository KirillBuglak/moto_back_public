package kb.moto.service.kafka

import jakarta.annotation.PostConstruct
import kb.moto.model.SentHistory
import kb.moto.model.kafka.EmailKafkaMessage
import kb.moto.service.EmailService
import kb.moto.service.abstr.AbstractPostgresService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class KafkaConsumerService @Autowired constructor(
    private val emailService: EmailService,
    private val sentHistoryService: AbstractPostgresService<SentHistory>,
    private val consumer: ReactiveKafkaConsumerTemplate<String, EmailKafkaMessage>)  {


    @PostConstruct
    fun consume() {
        val kafkaFlux = consumer
            .receive()
            .doOnError{ e -> System.err.println("Error processing message $e") }
            .flatMap {
                emailService.sendSimpleEmail(it.value())
            }
            .doOnError{ e -> System.err.println("Error sending message $e") }
            .flatMap {
                sentHistoryService.create(
                    SentHistory(it.first, it.second, LocalDateTime.now())
                )
            }

        kafkaFlux.subscribe()
    }
}
