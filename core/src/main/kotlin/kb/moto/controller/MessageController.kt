package kb.moto.controller

import kb.moto.model.kafka.EmailKafkaMessage
import kb.moto.service.UserContactsService
import kb.moto.service.kafka.KafkaProducerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/test")
class MessageController @Autowired constructor(
    val producerService: KafkaProducerService,
    val contactsService: UserContactsService
){

    @PostMapping
    fun sendMessage(@RequestBody map: Map<String, Any>): Mono<String> =
        contactsService.getContactsByUserName(map["userName"] as String)
            .flatMap {
                producerService.sendMessage(
                    EmailKafkaMessage(
                        it.email,
                        map["subject"] as String,
                        map["templateName"] as String,
                        mapOf("name" to "${it.firstName} ${it.lastName}")
                    ))
            }.thenReturn("Message sent")
}