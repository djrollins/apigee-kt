package com.djrollins.apigeekt.dsl.common

import com.djrollins.apigeekt.model.NameValuePair

class NameValuePairsBuilder: Builder<List<NameValuePair>> {
    private val entries = mutableListOf<NameValuePair>()

    infix fun String.to(value: String) = entries.add(NameValuePair(this, value))

    // TODO warn on duplicate names
    override fun build(): List<NameValuePair> = entries
}

