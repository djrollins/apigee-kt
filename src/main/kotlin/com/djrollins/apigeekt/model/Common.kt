package com.djrollins.apigeekt.model

data class NameValuePair(val name: String, val value: String )

enum class AssignToType { REQUEST, RESPONSE, INFERRED }
