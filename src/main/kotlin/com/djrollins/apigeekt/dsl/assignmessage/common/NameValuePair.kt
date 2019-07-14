package com.djrollins.apigeekt.dsl.assignmessage.common

import com.djrollins.apigeekt.model.NameValuePair

class NameValuePairsBuilder {
    private val entries = mutableListOf<NameValuePair>()

    infix fun String.to(value: String) = entries.add(NameValuePair(this, value))

    // TODO warn on duplicate names
    fun build(): List<NameValuePair> = entries
}

