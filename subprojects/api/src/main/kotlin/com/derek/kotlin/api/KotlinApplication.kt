package com.derek.kotlin.api

import com.derek.kotlin.api.properties.BoardRpcProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@EnableConfigurationProperties(
    BoardRpcProperties::class
)

class KotlinApplication

fun main(args: Array<String>) {
    runApplication<KotlinApplication>(*args)
}
