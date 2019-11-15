package com.derek.kotlin.api.config

import com.derek.kotlin.api.properties.BoardRpcProperties
import com.derek.kotlin.protocol.board.BoardServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BoardRpcConfiguration {
    @Bean
    fun boardRpcChannel(boardRpcProperties: BoardRpcProperties): ManagedChannel {
        return ManagedChannelBuilder
            .forAddress(boardRpcProperties.url, boardRpcProperties.portValue)
            .usePlaintext()
            .build()
    }

    @Bean
    fun managerServiceStub(@Qualifier("boardRpcChannel") boardRpcChannel: ManagedChannel): BoardServiceGrpc.BoardServiceBlockingStub {
        return BoardServiceGrpc.newBlockingStub(boardRpcChannel)
    }
}