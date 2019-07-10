package com.djrollins.apigeekt.dsl.assignmessage

import com.djrollins.apigeekt.model.assignmessage.Add
import com.djrollins.apigeekt.model.assignmessage.Verb

interface AddQueryParams {
    fun queryParams(block: NameValuePairsBuilder.() -> Unit)
}

interface AddFormParams {
    fun formParams(block: NameValuePairsBuilder.() -> Unit)
}

interface AddHeaders {
    fun headers(block: NameValuePairsBuilder.() -> Unit)
}

interface AddGet : AddQueryParams, AddHeaders
interface AddPost : AddFormParams, AddHeaders

interface AddBuilder : AddPost, AddGet

class AddBuilderImpl : AddBuilder {
    private val headers = NameValuePairsBuilder()
    private val queryParams = NameValuePairsBuilder()
    private val formParams = NameValuePairsBuilder()

    override fun headers(block: NameValuePairsBuilder.() -> Unit) {
        headers.apply(block)
    }

    override fun queryParams(block: NameValuePairsBuilder.() -> Unit) {
        queryParams.apply(block)
    }

    override fun formParams(block: NameValuePairsBuilder.() -> Unit) {
        formParams.apply(block)
    }

    fun build(): Add = Add(Verb.GET, headers.build(), queryParams.build(), formParams.build())
}

