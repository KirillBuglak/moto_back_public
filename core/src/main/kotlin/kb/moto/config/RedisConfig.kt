package kb.moto.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import java.time.Duration


@Configuration
class RedisConfig{

    @Bean
    fun reactiveRedisConnectionFactory(
        @Value("\${spring.redis.host}") host: String,
        @Value("\${spring.redis.port}") port: Int,
        @Value("\${spring.redis.password}") password: String
    ): LettuceConnectionFactory {
        val configuration = RedisStandaloneConfiguration(host, port)
        configuration.password = RedisPassword.of(password)
//        configuration.username = "yourusername" // Optional
        return LettuceConnectionFactory(configuration)
    }

    @Bean
    fun cacheManager(factory: LettuceConnectionFactory): RedisCacheManager {

        val defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()

        return RedisCacheManager.builder(factory)
            .withCacheConfiguration("parts", defaultCacheConfig.entryTtl(Duration.ofSeconds(120)))
            .withCacheConfiguration("users", defaultCacheConfig.entryTtl(Duration.ofSeconds(240)))
            //add here cacheNames with ttl config
            .build()
    }
}