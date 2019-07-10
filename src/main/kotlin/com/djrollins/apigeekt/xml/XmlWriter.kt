@file:Suppress("NAME_SHADOWING")

package com.djrollins.apigeekt.xml

import com.djrollins.apigeekt.model.assignmessage.*
import java.io.Writer

const val INDENT = "    "

class XmlWriter<T : Writer> private constructor(val writer: T, private val lineWriter: (String) -> Unit) {

    constructor(writer: T) : this(writer, writer::write)

    fun writeXmlBlock(tag: String, vararg attrs: Pair<String, String>, block: (writer: XmlWriter<T>) -> Unit) {
        val attrString = attrs.joinToString("") { """ ${it.first}="${it.second}"""" }

        writeLn("<$tag$attrString>")
        block(XmlWriter(writer) { lineWriter("$INDENT$it") })
        writeLn("</$tag>")
    }

    fun writeLn(str: String) = lineWriter("$str\n")
}

fun <T : Writer> AssignMessage.toXml(writer: XmlWriter<T>) {
    val attrs = arrayOf(
            Pair("name", name),
            Pair("continueOnError", continueOnError.toString()),
            Pair("enabled", enabled.toString())
    )

    writer.writeXmlBlock("AssignMessage", *attrs) { writer ->
        writer.writeLn("""<DisplayName>$name</DisplayName>""")
        writer.writeLn("""<IgnoreUnresolvedVariables>$ignoreUnresolvedVariables</IgnoreUnresolvedVariables>""")
        assignTo.toXml(writer)
        assignVariables.forEach { it.toXml(writer) }
        add.toXml(writer)
        copy.toXml(writer)
    }
}

private fun <T : Writer> AssignTo?.toXml(writer: XmlWriter<T>) {
    this?.let {
        val typeStr: String = type?.let { """ type="$type"""" }.orEmpty()
        writer.writeLn("""<AssignTo createNew="$createNew"$typeStr>${variable.identifier}</AssignTo>""")
    }
}

private fun <T : Writer> AssignVariable.toXml(writer: XmlWriter<T>) {
    writer.writeXmlBlock("AssignVariable") {
        it.writeLn("""<Name>$name</Name>""")
        it.writeLn("""<$type>$value</$type>""")
    }
}

private fun <T : Writer> List<NameValuePair>.toXml(writer: XmlWriter<T>, parentTag: String, childTag: String = parentTag.dropLast(1)) {
    if (this.isNotEmpty()) {
        writer.writeXmlBlock(parentTag) { writer ->
            this.forEach { header ->
                writer.writeLn("""<$childTag name="${header.name}">${header.value}</$childTag>""")
            }
        }
    }
}

private fun <T : Writer> Add.toXml(writer: XmlWriter<T>) {
    writer.writeXmlBlock("Add") {
        headers.toXml(it, "Headers")
        queryParams.toXml(it, "QueryParams")
        formParams.toXml(it, "FormParams")
    }
}

private fun <T : Writer> Copy.toXml(writer: XmlWriter<T>) {
    writer.writeLn("<!--TODO implement Copy.toXml: $this-->")
}
