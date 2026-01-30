package kb.moto.model.auth

import com.fasterxml.jackson.annotation.JsonIgnore
import kb.moto.model.ids.StringId
import lombok.Setter
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document(collection = "users")
data class User(
    @Indexed(unique = true)
    var username: String,
    @Setter
    private val password: String?,
    @DBRef
    val roles: Set<Role>?,
    @DBRef
    val authorities: Set<Authority>?
): StringId(), Serializable {

    @JsonIgnore
    fun getPass() = password ?: "password"
}