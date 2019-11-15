package com.derek.kotlin.api.config

import com.derek.kotlin.api.properties.BoardRpcProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://127.0.0.1:8081")
            .allowedOrigins("http://localhost:8081")
            .allowedOrigins("http://192.168.64.3:30001")
            .allowedOrigins("http://192.168.64.3:80")
            .allowedOrigins("http://192.168.64.3")
    }
}

val BoardRpcProperties.portValue
    get() = port.toInt()