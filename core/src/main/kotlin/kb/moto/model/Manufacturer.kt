package kb.moto.model

import kb.moto.model.ids.MotoId
import java.io.Serializable

data class Manufacturer(
    val name: String
): MotoId<Any>(), Serializable
