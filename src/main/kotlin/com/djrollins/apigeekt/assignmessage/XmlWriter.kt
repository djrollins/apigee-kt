@file:Suppress("NAME_SHADOWING")

package com.djrollins.apigeekt.assignmessage

import java.io.Writer

const val INDENT = "    "

fun AssignMessage.toXml(writer: Writer) {
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

    writer.writeLn("""</AssignMessage>""")
}

private fun AssignTo?.toXml(writer: Writer) {
    this?.let {
        val typeStr: String = type?.let { """ type="$type"""" }.orEmpty()
        writer.writeLn("""<AssignTo createNew="$createNew"$typeStr>${variable.identifier}</AssignTo>""")
    }
}

private fun AssignVariable.toXml(writer: Writer) {
    writer.writeXmlBlock("AssignVariable") {
        it.writeLn("""<Name>$name</Name>""")
        it.writeLn("""<$type>$value</$type>""")
    }
}

private fun Add.toXml(writer: Writer) {
    writer.writeXmlBlock("Add") {
        val paramsHeader = if (verb === Verb.GET) "QueryParams" else "FormParams"
        val paramHeader = if (verb === Verb.GET) "QueryParam" else "FormParam"

        if (headers.isNotEmpty()) {
            it.writeXmlBlock("Headers") { writer ->
                headers.forEach { header ->
                    writer.writeLn("""<Header name="${header.name}">${header.value}</Header>""")
                }
            }
        }

        if (params.isNotEmpty()) {
            it.writeXmlBlock(paramsHeader) { writer ->
                headers.forEach { param ->
                    writer.writeLn("""<$paramHeader name="${param.name}">${param.value}</Header>""")
                }
            }
        }
    }
}

private fun Copy.toXml(writer: Writer) {
    writer.writeLn("<!--TODO implement Copy.toXml: $this-->")
}

private fun Writer.writeLn(line: String) {
    this.write("$line\n")
}

private fun Writer.writeXmlBlock(tag: String, vararg attrs: Pair<String, String>, block: (writer: Writer) -> Unit) {
    val attrString = attrs.joinToString("") { """ ${it.first}="${it.second}"""" }

    this.writeLn("<$tag$attrString>")
    block(IndentedWriter(this, INDENT))
    this.writeLn("</$tag>")
}

class IndentedWriter<out T : Writer>(private val writer: T, private val indent: String) : Writer() {
    override fun write(p0: CharArray, p1: Int, p2: Int) {
        writer.write(p0, p1, p2)
    }

    override fun flush() {
        writer.flush()
    }

    override fun close() {
        writer.close()
    }

    override fun write(str: String) {
        writer.write("$indent$str")
    }
}