package com.djrollins.apigeekt.dsl.policy.assignmessage

import com.djrollins.apigeekt.dsl.common.Builder
import com.djrollins.apigeekt.model.Property
import com.djrollins.apigeekt.model.policy.assignmessage.*

/*
<AssignMessage continueOnError="false" enabled="true" name="assign-message-default">
  <DisplayName>Assign Message-1</DisplayName>
  <Properties/>
  <Copy/>
  <Remove/> <Add/>
  <Set/>
  <AssignVariable/>
  <IgnoreUnresolvedVariables>true</IgnoreUnresolvedVariables>
  <AssignTo createNew="false" transport="http" type="request"/>
</AssignMessage>
*/

fun assignMessage(name: String): AssignMessageDsl =
        AssignMessageDsl(name)

fun assignMessage(name: String, block: AssignMessageBuilder.() -> Unit): AssignMessage =
        AssignMessageBuilder(name, Inferred).apply(block).build()

class AssignMessageDsl(private val name: String) {
    val request by lazy { RequestDsl(name, Request) }

    fun request(block: AssignMessageRequestBuilder.() -> Unit): AssignMessage =
            AssignMessageRequestBuilder(name, Request).apply(block).build()

    fun response(block: AssignMessageResponseBuilder.() -> Unit): AssignMessage =
            AssignMessageResponseBuilder(name, Response).apply(block).build()
}

class RequestDsl(private val name: String, private val messageType: MessageType) {
    fun get(block: AssignMessageRequestGetBuilder.() -> Unit): AssignMessage =
            AssignMessageRequestGetBuilder(name, messageType).apply(block).build()

    fun post(block: AssignMessageRequestPostBuilder.() -> Unit): AssignMessage =
            AssignMessageRequestPostBuilder(name, messageType).apply(block).build()

}

class AssignMessageBuilder(name: String, messageType: MessageType) : AssignMessageBuilderBase(name, messageType) {
    fun add(block: AddBuilder.() -> Unit) = super.doAdd(block)
}

class AssignMessageResponseBuilder(name: String, messageType: MessageType) : AssignMessageBuilderBase(name, messageType) {
    fun add(block: AddHeaders.() -> Unit) = super.doAdd(block)
}

class AssignMessageRequestBuilder(name: String, messageType: MessageType) : AssignMessageBuilderBase(name, messageType) {
    fun add(block: AddBuilder.() -> Unit) = super.doAdd(block)
}

class AssignMessageRequestGetBuilder(name: String, messageType: MessageType) : AssignMessageBuilderBase(name, messageType) {
    fun add(block: AddGet.() -> Unit) = super.doAdd(block)
}

class AssignMessageRequestPostBuilder(name: String, messageType: MessageType) : AssignMessageBuilderBase(name, messageType) {
    fun add(block: AddPost.() -> Unit) = super.doAdd(block)
}

class AssignToBuilder(private var type: MessageType, var name: String, var createNew: Boolean = false) {
    fun build(): AssignTo = AssignTo(createNew, type, Property<Any>(name))
}

open class AssignMessageBuilderBase(private val name: String, private val type: MessageType): Builder<AssignMessage> {
    private var enabled: Boolean = true
    private var continueOnError: Boolean = false
    private val add = AddBuilderImpl()
    private val copy = CopyBuilder(type)
    private val assignVariables = AssignVariablesBuilder()
    private var assignTo: AssignTo? = null

    protected fun doAdd(block: AddBuilder.() -> Unit) = add.apply(block)

    fun copy(block: CopyBuilder.() -> Unit) = copy.apply(block)

    fun assignTo(block: AssignToBuilder.() -> Unit) {
        assignTo = AssignToBuilder(type, name).apply(block).build()
    }

    fun assignVariables(block: AssignVariablesBuilder.() -> Unit) = assignVariables.apply(block)

    override fun build(): AssignMessage {
        return AssignMessage(
                name,
                enabled,
                continueOnError,
                false,
                assignTo,
                assignVariables.build(),
                add.build(),
                copy.build(),
                null,
                null
        )
    }
}

