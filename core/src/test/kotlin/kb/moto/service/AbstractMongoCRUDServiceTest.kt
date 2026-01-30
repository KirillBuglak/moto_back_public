package kb.moto.service

import io.mockk.every
import io.mockk.mockk
import kb.moto.model.ids.StringId
import kb.moto.service.abstr.AbstractMongoCRUDService
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

// Minimal test entity for the generic service
data class TestEntity(override var id: String? = null, val value: String = "") : StringId()

class AbstractMongoCRUDServiceTest {

    @Test
    fun `get returns entity when found`() {
        val repo = mockk<ReactiveMongoRepository<TestEntity, String>>()
        val entity = TestEntity(id = "1", value = "v")
        every { repo.findById("1") } returns Mono.just(entity)

        val service = object : AbstractMongoCRUDService<TestEntity>(repo) {}

        StepVerifier.create(service.get("1"))
            .expectNext(entity)
            .verifyComplete()
    }

    @Test
    fun `get emits error when not found`() {
        val repo = mockk<ReactiveMongoRepository<TestEntity, String>>()
        every { repo.findById("1") } returns Mono.empty()

        val service = object : AbstractMongoCRUDService<TestEntity>(repo) {}

        StepVerifier.create(service.get("1"))
            .expectErrorMatches { it is RuntimeException && it.message?.contains("Oops") == true }
            .verify()
    }
}