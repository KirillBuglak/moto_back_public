package kb.moto.model.auth

import kb.moto.model.ids.StringId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document(collection = "roles")
class Role: StringId(), Serializable {

    @Indexed(unique = true)
    lateinit var name: String

    @DBRef
    lateinit var authorities: Set<Authority>
}