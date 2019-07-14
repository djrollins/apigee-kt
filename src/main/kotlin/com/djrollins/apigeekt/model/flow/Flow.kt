package com.djrollins.apigeekt.model.flow

import com.djrollins.apigeekt.model.Condition
import com.djrollins.apigeekt.model.Condition.Always
import com.djrollins.apigeekt.model.policy.Policy

data class Step(val policy: Policy, val condition: Condition = Always)

sealed class Flow(
        val name: String?,
        val description: String?,
        val requestSteps: List<Step>,
        val responseSteps: List<Step>,
        val condition: Condition = Always
) {
    class PreFlow(name: String?, description: String?, requestSteps: List<Step>, responseSteps: List<Step>) :
        Flow(name, description, requestSteps, responseSteps)

    class PostFlow(name: String?, description: String?, requestSteps: List<Step>, responseSteps: List<Step>) :
            Flow(name, description, requestSteps, responseSteps)

    class ConditionalFlow(name: String?, description: String?, requestSteps: List<Step>, responseSteps: List<Step>, condition: Condition) :
            Flow(name, description, requestSteps, responseSteps, condition)
}
