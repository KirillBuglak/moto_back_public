package kb.moto.model

import kb.moto.model.ids.MotoId
import java.io.Serializable

data class FrontSuspensionSpec(
    val type: String,
    val travel: Short
): MotoId<Any>(), Serializable
