package com.djrollins.apigeekt.model.assignmessage

data class NameValuePair(
        val name: String,
        val value: String
)

data class Path(val path: String)
data class Payload(val payload: String) // TODO make this some JSON type
data class ReasonPhrase(val reasonPhrase: String)
data class StatusCode(val statusCode: String) // TODO make this a strong type?
