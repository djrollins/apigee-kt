package com.djrollins.apigeekt.model.proxy

import com.djrollins.apigeekt.model.flow.Flow
import java.nio.file.Path

data class ProxyEndpoint(
        val name: String,
        val connection: HttpProxyConnection,
        val preFlow: Flow.PreFlow,
        val flows: List<Flow.ConditionalFlow>,
        val postFlow: Flow.PostFlow,
        val faultRules: List<Any>, // TODO
        val defaultFaultRules: List<Any>, // TODO
        val routeRules: List<Any>
)

data class VirtualHost(val name: String)

data class HttpProxyConnection(val basePath: Path, val virtualHost: VirtualHost)

