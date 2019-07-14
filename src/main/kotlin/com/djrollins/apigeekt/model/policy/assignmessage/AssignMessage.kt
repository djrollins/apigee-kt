package com.djrollins.apigeekt.model.policy.assignmessage

import com.djrollins.apigeekt.common.Assignable
import com.djrollins.apigeekt.model.NameValuePair
import com.djrollins.apigeekt.model.Property
import com.djrollins.apigeekt.model.policy.Policy

data class AssignMessage(
        override val name: String,
        val enabled: Boolean,
        val continueOnError: Boolean,
        val ignoreUnresolvedVariables: Boolean,
        val assignTo: AssignTo?,
        val assignVariables: List<AssignVariable<Assignable>>,
        val add: Add,
        val copy: Copy,
        val remove: Any?,
        val set: Any?
): Policy

sealed class MessageType
object Request : MessageType() {
    override fun toString(): String = "request"
}

object Response : MessageType() {
    override fun toString(): String = "response"
}

object Inferred : MessageType() {
    override fun toString(): String = "response"
}

enum class Verb { GET, POST }

data class Add(
        val verb: Verb,
        val headers: List<NameValuePair>,
        val queryParams: List<NameValuePair>,
        val formParams: List<NameValuePair>
)

data class AssignTo(
        val createNew: Boolean,
        val type: MessageType?,
        val property: Property<*>?
)

data class AssignVariable<T : Assignable>(val name: String, val value: T) {
    val tag = value.tag
}

// params represents FormParams if this is a Post RequestDsl and QueryParams if this is a Get RequestDsl
// TODO are headers and params actually lists here or are the docs lying again?
data class Copy(
        val source: MessageType,
        val headers: List<NameValuePair>,
        val params: List<NameValuePair>, // TODO Requests only,
        val path: Boolean, // TODO Requests only
        val payload: Boolean,
        val reasonPhrase: Boolean, // TODO Response only
        val statusCode: Boolean, // TODO Response only
        val verb: Boolean, // TODO RequestDsl only
        val version: Boolean // TODO RequestDsl only
)
