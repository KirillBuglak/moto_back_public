package kb.moto.model

import kb.moto.model.ids.MotoId
import java.io.Serializable

data class EngineSpec(
    val type: String,
    val cylinderNumber: Byte,
    val bore: Float,
    val stroke: Float,
    val valveSystemType: String,
    val valvesPerCylinder: Byte,
    val maxPower: Short,
    val maxTorque: Short
): MotoId<Any>(), Serializable
