package com.derek.kotlin.grpc.config

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
class ExposedConfiguration{
    @Bean
    fun transactionManager(dataSource: HikariDataSource): SpringTransactionManager = SpringTransactionManager(dataSource)


    @Bean // @Repository 클래스들에 대해 자동으로 예외를 Spring의 DataAccessException으로 일괄 변환해준다.
    fun persistenceExceptionTranslationPostProcessor() = PersistenceExceptionTranslationPostProcessor()
}