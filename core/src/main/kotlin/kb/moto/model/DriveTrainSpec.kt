package kb.moto.model

import com.fasterxml.jackson.databind.JsonNode
import kb.moto.model.ids.MotoId
import java.io.Serializable

data class DriveTrainSpec(
    val gearNumber: Byte,
    val finalDrive: String,
    val primaryReduction: Float,
    val gearRatio: JsonNode
): MotoId<Any>(), Serializable
