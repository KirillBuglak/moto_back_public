package kb.moto.model

import kb.moto.model.ids.MotoId
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable

@Table("brake_spec")
data class BrakeSpec(
    val type: String
): MotoId<Any>(), Serializable {
    init {
        System.err.println("brake_spec " + System.nanoTime())
    }
}
