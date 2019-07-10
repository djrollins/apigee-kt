package com.djrollins.apigeekt.model.assignmessage

import com.djrollins.apigeekt.model.NameValuePair
import com.djrollins.apigeekt.model.Variable

data class AssignMessage(
        val name: String,
        val enabled: Boolean,
        val continueOnError: Boolean,
        val ignoreUnresolvedVariables: Boolean,
        val assignTo: AssignTo?,
        val assignVariables: List<AssignVariable>,
        val add: Add,
        val copy: Copy,
        val remove: Any?,
        val set: Any?
)

sealed class MessageType
object Request : MessageType() {
    override fun toString(): String = "request"
}

object Response : MessageType() {
    override fun toString(): String = "response"
}

object Any : MessageType() {
    override fun toString(): String = "response"
}


enum class Verb { GET, POST }

// TODO Create sub types for RequestDsl/Response and GET/POST for Add, Copy etc so only valid members can be used

// params represents FormParams if this is a Post RequestDsl and QueryParams if this is a Get RequestDsl
data class Add(
        val verb: Verb,
        val headers: List<NameValuePair>,
        val queryParams: List<NameValuePair>, // TODO Requests only
        val formParams: List<NameValuePair>
)

data class AssignTo(
        val variable: Variable,
        val createNew: Boolean,
        val type: MessageType?
)

enum class VariableType { Ref, Value, Template }
data class AssignVariable(val type: VariableType, val name: String, val value: String)

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
