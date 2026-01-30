package kb.moto.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.SessionLimit
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler
import reactor.core.publisher.Mono


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(useAuthorizationManager=true)
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        val httpBasic = http.authorizeExchange {
            it.pathMatchers("/actuator/**").permitAll()
            it.pathMatchers("/test/**").permitAll()
            it.pathMatchers("/front_test/**").permitAll()
            it.pathMatchers("/auth/**").permitAll()
            it.pathMatchers("/contacts/**").permitAll()
            it.pathMatchers("/admin/authorities/**").hasRole("ADMIN") //todo may need to change to authenticated
            it.pathMatchers("/admin/**").authenticated() //@PreAuthorize useful when different methods require different roles, for instance
            it.pathMatchers("/parts/**").authenticated()
            it.pathMatchers("/manufacturer/**").authenticated()
            it.pathMatchers("/model/**").authenticated()
            it.pathMatchers("/config/**").hasRole("ADMIN") //useful when we need to grant access to all methods of this controller
            it.anyExchange().denyAll()
        }
            .addFilterBefore(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .logout {
                it.logoutHandler(
                    DelegatingServerLogoutHandler(
                        SecurityContextServerLogoutHandler(),
                        WebSessionServerLogoutHandler()
                    )
                )
            }
            .sessionManagement { spec ->
//            spec.concurrentSessions {
//                it.maximumSessions(maxSessions())
//                it.maximumSessionsExceededHandler(PreventLoginServerMaximumSessionsExceededHandler())
//                it.sessionRegistry(repository)
//            }
            }
            .httpBasic {
//                withDefaults()
                it.authenticationEntryPoint(noAuthHeaderEntryPoint())
            }
            .csrf { it.disable() }
            .authenticationManager(reactiveAuthenticationManager())

        return httpBasic.build()
    }

    private fun maxSessions(): SessionLimit {
        return SessionLimit { authentication: Authentication ->
            if (authentication.authorities.contains(SimpleGrantedAuthority("ROLE_ULTIMATE"))) {
                return@SessionLimit Mono.empty<Int?>() // allow unlimited sessions for users with ROLE_ULTIMATE
            }
            if (authentication.authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return@SessionLimit Mono.just<Int?>(2) // allow two sessions for admins
            }
            Mono.just(1) // allow one session for every other user
        }
    }

    @Bean
    fun noAuthHeaderEntryPoint(): ServerAuthenticationEntryPoint {
        return ServerAuthenticationEntryPoint { exchange, _ ->
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            // Do not set WWW-Authenticate header!
            Mono.empty<Void>()
        }
    }

    @Bean
    fun reactiveAuthenticationManager() = ReactiveAuthenticationManagerImpl()

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun serverAuthenticationConverter() = ServerAuthenticationConverterImpl()

    @Bean
    fun authenticationWebFilter(): AuthenticationWebFilter {
        val authenticationManager = reactiveAuthenticationManager()

        val authenticationWebFilter = AuthenticationWebFilter(authenticationManager)
        authenticationWebFilter.setServerAuthenticationConverter(serverAuthenticationConverter())

        return authenticationWebFilter
    }
}