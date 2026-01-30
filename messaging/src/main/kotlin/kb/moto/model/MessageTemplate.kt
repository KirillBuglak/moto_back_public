package kb.moto.model

import kb.moto.model.ids.MotoId

data class MessageTemplate(
    val name: String,
    val text: String
): MotoId<Any>()
