package kb.moto.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect

@Configuration
class Config {
    @Bean
    fun objectMapper() = ObjectMapper()

    @Bean
    fun customConversions(): R2dbcCustomConversions {
        return R2dbcCustomConversions.of(
            PostgresDialect.INSTANCE,
            listOf(JsonToJsonNodeConverter(objectMapper()), JsonNodeToJsonConverter(objectMapper()))
            )
    }
}