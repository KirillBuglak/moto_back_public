package kb.moto.jwt

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import kb.moto.model.auth.Role
import kb.moto.model.auth.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpCookie
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import java.util.*

@Component
class JwtUtil {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val logger = LoggerFactory.getLogger(javaClass)

    private val SECRET_KEY = "superSecretKey2000AndOtherLettersToMakeItEnoughForANormalKey"
    private val KEY = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray())

    @Value("\${jjwt.validity.time}")
    val TOKEN_VALIDITY_TIME: Long? = null

    fun generateToken(user: User): String {

        val auths = collectRolesAndAuthsToSet(user)!!.plus(collectAuthsToSet(user)).toSet()

        return doGenerateToken(
            mapOf(
                "AUTHORITIES" to auths
            ),
            user.username
        )
    }

    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).payload
    }

    private fun isTokenExpired(token: String) = getTokenExpDate(token).before(Date())

    private fun getUsernameFromToken(token: String): String = getClaimsFromToken(token).subject

    fun getUsernamePasswordAuthenticationToken(token: String): UsernamePasswordAuthenticationToken {
        val username = getUsernameFromToken(token)

        val authSequence = getAuthSequence(token)
        val authorities = authSequence.map { SimpleGrantedAuthority(it.asText()) }.toSet()

        return UsernamePasswordAuthenticationToken(username, null, authorities)
    }

    private fun getAuthSequence(token: String): Sequence<JsonNode> {
        val v = getClaimsFromToken(token)["AUTHORITIES"]
        val jsonTree = objectMapper.valueToTree<JsonNode>(v)
        return jsonTree.elements().asSequence()
    }

    private fun collectAuthsToSet(user: User): Sequence<String> {
        val seq = user.authorities!!.asSequence()
            .map { it.name }
        return seq
    }

    private fun collectRolesAndAuthsToSet(user: User): Sequence<String> {
        val seq = user.roles!!.asSequence()
            .map { e -> getAuthStrings(e) }
            .flatMap { it }
        return seq
    }

    private fun getAuthStrings(e: Role) =
        e.authorities.asSequence().map { it.name }.plus("ROLE_${e.name}").toSet()

    private fun getTokenExpDate(token: String): Date = getClaimsFromToken(token).expiration

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().verifyWith(KEY).build().parse(token)
            return true
        } catch (e: MalformedJwtException) {
            logger.info("Invalid JWT token: " + e.message)
        } catch (e: ExpiredJwtException) {
            logger.info("JWT token is expired: " + e.message)
        } catch (e: UnsupportedJwtException) {
            logger.info("JWT token is unsupported: " + e.message)
        } catch (e: IllegalArgumentException) {
            logger.info("JWT claims string is empty: " + e.message)
        }
        return false
    }

    private fun doGenerateToken(claims: Map<String, Any>, username: String): String {
        return Jwts.builder()
            .claims(claims)
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + TOKEN_VALIDITY_TIME!!))
            .signWith(KEY)
            .compact()
    }

    fun getJwtCookie(exchange: ServerWebExchange): HttpCookie? {
        val cookies = exchange.request.cookies
        return cookies.getFirst("JWT")
    }

    fun getAuthoritiesFromToken(token: String): Set<String> = getAuthSequence(token).map { it.asText() }.toSet()
}