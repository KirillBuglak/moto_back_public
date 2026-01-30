package kb.moto.model

import kb.moto.model.ids.MotoId
import java.io.Serializable

data class ChassisSpec(
    val frameType: String,
    val trail: Float,
    val rake: Float?,
    val length: Short,
    val width: Short,
    val height: Short,
    val seatHeight: Short,
    val wheelBase: Short,
    val groundClearance: Short,
    val dryWeight: Short,
    val wetWeight: Short
): MotoId<Any>(), Serializable
