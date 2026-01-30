package kb.moto.model

import kb.moto.model.ids.MotoId
import java.io.Serializable

data class MotoModel(
    val name: String,
    val manufacturer: Long,
    val startYear: Short,
    val finishYear: Short?,
    val engine: Long,
    val fuelSystem: Long,
    val frontSuspension: Long,
    val rearSuspension: Long,
    val frontBrake: Long,
    val rearBrake: Long,
    val driveTrain: Long,
    val chassis: Long,
    val electricalSystem: Long
): MotoId<Any>(), Serializable
