package kb.moto.model.auth

import kb.moto.model.ids.StringId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document(collection = "user_contacts")
data class UserContacts(
    @Indexed(unique = true)
    var username: String,
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val phone: String,
    val email: String
): StringId(), Serializable
