package kb.moto.model

import kb.moto.model.ids.MotoId
import java.io.Serializable

data class FuelSystemSpec(
    val type: String,
    val tankCapacity: Byte
): MotoId<Any>(), Serializable
