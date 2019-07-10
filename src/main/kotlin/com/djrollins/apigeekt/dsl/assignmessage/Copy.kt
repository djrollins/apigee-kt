package com.djrollins.apigeekt.dsl.assignmessage

import com.djrollins.apigeekt.model.assignmessage.Copy
import com.djrollins.apigeekt.model.assignmessage.MessageType

class CopyBuilder(private val source: MessageType) {
    private val headers by lazy { NameValuePairsBuilder() }
    private val params by lazy { NameValuePairsBuilder() }
    var path: Boolean = false
    var payload: Boolean = false
    var reasonPhrase: Boolean = false
    var statusCode: Boolean = false
    var verb: Boolean = false
    var version: Boolean = false

    fun headers(block: NameValuePairsBuilder.() -> Unit) = headers.apply(block)
    fun params(block: NameValuePairsBuilder.() -> Unit) = params.apply(block)

    fun build(): Copy = Copy(
            source,
            headers.build(),
            params.build(),
            path,
            payload,
            reasonPhrase,
            statusCode,
            verb,
            version
    )
}

