package kb.moto.model

import kb.moto.model.ids.MotoId
import java.time.LocalDateTime

data class SentHistory(
    val recipient: String,
    val text: String,
    val cdat: LocalDateTime,
): MotoId<Any>()
