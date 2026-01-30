package kb.moto.model

import kb.moto.model.ids.MotoId
import java.io.Serializable

data class RearSuspensionSpec(
    val type: String,
    val travel: Short
): MotoId<Any>(), Serializable
