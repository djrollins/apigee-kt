package com.djrollins.apigeekt.model.proxy

import com.djrollins.apigeekt.model.Condition
import java.net.URL

sealed class RouteRuleTarget {
    data class TargetEndpoint(val name: String) : RouteRuleTarget()
    data class Url(val url: URL) : RouteRuleTarget()
}

data class RouteRule(val name: String, val target: RouteRuleTarget, val condition: Condition)
