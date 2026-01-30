package kb.moto.model.ids

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import java.io.Serializable

@JsonPropertyOrder("id")
abstract class MotoId<T>: Serializable {
    @Id
    var id: T? = null
}