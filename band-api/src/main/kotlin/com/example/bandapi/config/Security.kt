package com.example.bandapi.config

import com.example.bandapi.security.CustomUserDetailsService
import com.example.bandapi.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(
        userDetailsService: CustomUserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationProvider {

        val provider = DaoAuthenticationProvider(userDetailsService)

        provider.setPasswordEncoder(passwordEncoder)

        return provider
    }

    @Bean
    fun authenticationManager(
        configuration: AuthenticationConfiguration
    ): AuthenticationManager {

        return configuration.authenticationManager
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter,
        authenticationProvider: AuthenticationProvider
    ): SecurityFilterChain {

        http
            .csrf { it.disable() }

            .sessionManagement {
                it.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }

            .authenticationProvider(authenticationProvider)

            .authorizeHttpRequests {

                it
                    .requestMatchers(
                        "/users",
                        "/users/**",
                        "/auth/**",
                        "/error"
                    )
                    .permitAll()

                    .anyRequest()
                    .authenticated()
            }

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }
}