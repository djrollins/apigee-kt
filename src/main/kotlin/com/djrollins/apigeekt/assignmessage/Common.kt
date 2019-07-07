package com.djrollins.apigeekt.assignmessage

 class NameValuePair(
        val name: String,
        val value: String
)

inline class Path(val path: String)
inline class Payload(val payload: String) // TODO make this some JSON type
inline class ReasonPhrase(val reasonPhrase: String)
inline class StatusCode(val statusCode: String) // TODO make this a strong type?
