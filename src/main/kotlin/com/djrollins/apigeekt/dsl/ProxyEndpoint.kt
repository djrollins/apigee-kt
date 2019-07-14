package com.djrollins.apigeekt.dsl

import com.djrollins.apigeekt.dsl.flow.FlowsBuilder
import com.djrollins.apigeekt.dsl.flow.PostFlowBuilder
import com.djrollins.apigeekt.dsl.flow.PreFlowBuilder
import com.djrollins.apigeekt.model.Condition
import com.djrollins.apigeekt.model.proxy.*
import java.nio.file.Path
import java.nio.file.Path.of

/*
<ProxyEndpoint name="default">
  <PreFlow/>
  <Flows/>
  <PostFlow/>
  <HTTPProxyConnection>
    <BasePath>/weather</BasePath>
    <VirtualHost>default</VirtualHost>
  </HTTPProxyConnection>
  <FaultRules/>
  <DefaultFaultRule/>
  <RouteRule name="default">
    <TargetEndpoint>default</TargetEndpoint>
  </RouteRule>
</ProxyEndpoint>
*/

fun proxyEndpoint(block: ProxyEndpointBuilder.() -> Unit): ProxyEndpoint =
        ProxyEndpointBuilder().apply(block).build()

@DslMarker
annotation class ProxyEndpointMarker

@ProxyEndpointMarker
class ProxyEndpointBuilder {
    var name = "default"
    lateinit var connection: HttpProxyConnection
    private val preFlow = PreFlowBuilder()
    private val postFlow = PostFlowBuilder()
    private val flows = FlowsBuilder()
    private val routeRules = mutableListOf<RouteRule>()

    fun connection(block: HttpProxyConnectionBuilder.() -> Unit) {
        connection = HttpProxyConnectionBuilder().apply(block).build()
    }

    fun routeRule(block: RouteRuleBuilder.() -> Unit) {
        routeRules.add(RouteRuleBuilder().apply(block).build())
    }

    fun preFlow(block: PreFlowBuilder.() -> Unit) {
        preFlow.apply(block)
    }

    fun postFlow(block: PostFlowBuilder.() -> Unit) {
        postFlow.apply(block)
    }

    fun flows(block: FlowsBuilder.() -> Unit) {
        flows.apply(block)
    }

    fun build(): ProxyEndpoint = ProxyEndpoint(
            name,
            connection,
            preFlow.build(),
            flows.build(),
            postFlow.build(),
            emptyList(),
            emptyList(),
            routeRules
    )
}

class RouteRuleBuilder {
    var name = "default"
    lateinit var target: RouteRuleTarget
    var condition: Condition? = null

    fun build(): RouteRule = RouteRule(name, target, condition ?: Condition.Always)
}

class HttpProxyConnectionBuilder {
    var basePath: Path = of("/")
    lateinit var virtualHost: VirtualHost

    fun build(): HttpProxyConnection = HttpProxyConnection(basePath, virtualHost)
}

