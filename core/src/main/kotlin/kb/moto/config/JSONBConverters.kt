package kb.moto.config

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter

class JsonToJsonNodeConverter(val objectMapper: ObjectMapper): Converter<Json, JsonNode> {

    override fun convert(source: Json): JsonNode {
        try {
            return objectMapper.readTree(source.asString())
        } catch (e: Exception) {
            throw RuntimeException("Failed to convert Json to JsonNode", e)
        }
    }
}


class JsonNodeToJsonConverter(val objectMapper: ObjectMapper): Converter<JsonNode, Json> {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun convert(source: JsonNode): Json {
        try {
            return Json.of(objectMapper.writeValueAsString(source))
        } catch (e: Exception) {
            throw RuntimeException("Failed to convert JsonNode to Json", e)
        }
    }
}