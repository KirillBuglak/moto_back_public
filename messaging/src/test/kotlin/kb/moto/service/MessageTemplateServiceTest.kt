package kb.moto.service

import io.mockk.every
import io.mockk.mockk
import kb.moto.model.MessageTemplate
import kb.moto.repo.MessageTemplateRepo
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class MessageTemplateServiceTest {

    @Test
    fun `find returns template when exists`() {
        val repo = mockk<MessageTemplateRepo>()
        val template = MessageTemplate(name = "welcome", text = "Hello \${name}")
        every { repo.findByName("welcome") } returns Mono.just(template)

        val service = MessageTemplateService(repo)

        StepVerifier.create(service.find("welcome"))
            .expectNext(template)
            .verifyComplete()
    }

    @Test
    fun `find emits error when template not found`() {
        val repo = mockk<MessageTemplateRepo>()
        every { repo.findByName("missing") } returns Mono.empty()

        val service = MessageTemplateService(repo)

        StepVerifier.create(service.find("missing"))
            .expectErrorMatches { it is RuntimeException && it.message == "no such template" }
            .verify()
    }
}