package com.djrollins.apigeekt.dsl.policy.assignmessage

import com.djrollins.apigeekt.dsl.common.Builder
import com.djrollins.apigeekt.dsl.common.NameValuePairsBuilder
import com.djrollins.apigeekt.model.policy.assignmessage.Add
import com.djrollins.apigeekt.model.policy.assignmessage.Verb

/*
  <Add>
    <FormParams>
      <FormParam name="formparam_name">formparam_value</FormParam>
    </FormParams>
    <Headers>
      <Header name="header_name">header_value</Header>
    </Headers>
    <QueryParams>
      <QueryParam name="queryparam_name">queryparam_value</QueryParam>
    </QueryParams>
  </Add>
*/

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

class AddBuilderImpl : AddBuilder, Builder<Add> {
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

    override fun build(): Add = Add(Verb.GET, headers.build(), queryParams.build(), formParams.build())
}

