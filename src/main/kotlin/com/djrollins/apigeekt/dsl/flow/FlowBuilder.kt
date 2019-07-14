package com.djrollins.apigeekt.dsl.flow

import com.djrollins.apigeekt.dsl.common.Builder
import com.djrollins.apigeekt.model.Condition
import com.djrollins.apigeekt.model.flow.Flow.*
import com.djrollins.apigeekt.model.flow.Step
import com.djrollins.apigeekt.model.policy.Policy

class FlowsBuilder: Builder<List<ConditionalFlow>> {
    var flows = mutableListOf<ConditionalFlow>()

    fun flow(block: ConditionalFlowBuilder.() -> Unit) {
        flows.add(ConditionalFlowBuilder().apply(block).build())
    }

    override fun build(): List<ConditionalFlow> = flows
}

sealed class FlowBuilderBase {
    var name: String? = null
    var description: String? = null
    protected val requestSteps = StepsBuilder()
    protected val responseSteps = StepsBuilder()

    fun request(block: StepsBuilder.() -> Unit) {
        requestSteps.apply(block)
    }

    fun response(block: StepsBuilder.() -> Unit) {
        responseSteps.apply(block)
    }
}

class PreFlowBuilder : FlowBuilderBase(), Builder<PreFlow> {
    override fun build(): PreFlow = PreFlow(name, description, requestSteps.build(), responseSteps.build())
}

class PostFlowBuilder : FlowBuilderBase(), Builder<PostFlow> {
    override fun build(): PostFlow = PostFlow(name, description, requestSteps.build(), responseSteps.build())
}

class ConditionalFlowBuilder : FlowBuilderBase(), Builder<ConditionalFlow> {
    var condition: Condition = Condition.Always

    override fun build(): ConditionalFlow = ConditionalFlow(name, description, requestSteps.build(), responseSteps.build(), condition)
}

class StepsBuilder {
    val steps = mutableListOf<Step>()

    fun step(policy: Policy, condition: Condition = Condition.Always) {
        steps.add(Step(policy, condition))
    }

    fun step(block: StepBuilder.() -> Unit) {
        steps.add(StepBuilder().apply(block).build())
    }

    fun build(): List<Step> = steps
}

class StepBuilder {
    lateinit var policy: Policy
    var condition: Condition = Condition.Always

    fun build(): Step = Step(policy, condition)
}
