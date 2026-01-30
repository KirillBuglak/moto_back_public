package kb.moto.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.WebFluxConfigurer

@Profile("dev")
@Configuration
class WebConfig : WebFluxConfigurer {

    //    override fun addCorsMappings(registry: CorsRegistry) {
//        registry.addMapping("/**") // Apply to all paths
//            .allowedOrigins("http://localhost:3000", "http://localhost:3002") // Your React app's origins
//            .allowedMethods("*") // Allow common HTTP methods
//            .allowedHeaders("*") // Allow all headers
//            .allowCredentials(true) // If sending cookies (like JWT)
//            .maxAge(3600) // Optional: Cache preflight for 1 hour
//    }
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:3002")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}