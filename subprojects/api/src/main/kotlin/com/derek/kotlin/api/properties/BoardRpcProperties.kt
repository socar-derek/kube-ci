package com.derek.kotlin.api.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@ConfigurationProperties("rpc.board")
class BoardRpcProperties{
    lateinit var url: String
    lateinit var port: String
}