package kb.moto.model

import kb.moto.model.ids.MotoId
import java.io.Serializable

data class ElectricalSystemSpec(
    val ignition: String,
    val alternator: String,
    val batteryCapacity: String
): MotoId<Any>(), Serializable
