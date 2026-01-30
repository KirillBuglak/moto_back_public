package kb.moto.model.auth

import kb.moto.model.ids.StringId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document(collection = "authorities")
data class Authority(
    @Indexed(unique = true)
    var name: String
): StringId(), Serializable
