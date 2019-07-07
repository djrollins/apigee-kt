package com.djrollins.apigeekt.assignmessage

import com.djrollins.apigeekt.Variable

sealed class MessageType
object Request : MessageType() {
    override fun toString(): String = "request"
}

object Response : MessageType() {
    override fun toString(): String = "response"
}

enum class Verb { GET, POST }

// TODO Create sub types for Request/Response and GET/POST for Add, Copy etc so only valid members can be used

// params represents FormParams if this is a Post Request and QueryParams if this is a Get Request
data class Add(
        val verb: Verb,
        val headers: List<NameValuePair>,
        val params: List<NameValuePair> // TODO Requests only
)

data class AssignTo(
        val variable: Variable,
        val createNew: Boolean,
        val type: MessageType?
)

enum class VariableType { Ref, Value, Template }
data class AssignVariable(val type: VariableType, val name: String, val value: String)

// params represents FormParams if this is a Post Request and QueryParams if this is a Get Request
// TODO are headers and params actually lists here or are the docs lying again?
data class Copy(
        val source: MessageType,
        val headers: List<NameValuePair>,
        val params: List<NameValuePair>, // TODO Requests only,
        val path: Boolean, // TODO Requests only
        val payload: Boolean,
        val reasonPhrase: Boolean, // TODO Response only
        val statusCode: Boolean, // TODO Response only
        val verb: Boolean, // TODO Request only
        val version: Boolean // TODO Request only
)
