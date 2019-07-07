package com.djrollins.apigeekt

import com.djrollins.apigeekt.assignmessage.*
import java.util.logging.Logger

fun assignMessage(name: String, type: MessageType, block: AssignMessageBuilder.() -> Unit): AssignMessage =
        AssignMessageBuilder(name, type).apply(block).build()


// TODO these `by lazy` invocations are unnecessary. Just initialize the values on construction.
class AssignMessageBuilder(private val name: String, private val type: MessageType) {
    private var enabled: Boolean = true
    private var continueOnError: Boolean = false
    private val add by lazy { AddBuilder() }
    private val copy by lazy { CopyBuilder(type) }
    private val assignVariables by lazy { AssignVariablesBuilder() }
    private var assignTo: AssignTo? = null

    fun add(block: AddBuilder.() -> Unit) = add.apply(block)
    fun copy(block: CopyBuilder.() -> Unit) = copy.also(block)

    fun assignTo(variable: Variable) {
        // TODO parameterize these
        assignTo = AssignTo(variable, true, null)
    }

    fun assignVariables(block: AssignVariablesBuilder.() -> Unit) = assignVariables.apply(block)

    fun build(): AssignMessage {
        return AssignMessage(
                name,
                enabled,
                continueOnError,
                false,
                assignTo,
                assignVariables.build(),
                add.build(),
                copy.build(),
                null,
                null
        )
    }
}

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

class AddBuilder {
    private val headers by lazy { NameValuePairsBuilder() }
    private val params by lazy { NameValuePairsBuilder() }

    fun headers(block: NameValuePairsBuilder.() -> Unit) = headers.apply(block)
    fun params(block: NameValuePairsBuilder.() -> Unit) = params.apply(block)

    fun build(): Add = Add(Verb.GET, headers.build(), params.build())
}

class NameValuePairsBuilder {
    private val entries by lazy { mutableListOf<NameValuePair>() }

    infix fun String.to(value: String) = entries.add(NameValuePair(this, value))

    // TODO warn on duplicate names
    fun build(): List<NameValuePair> = entries
}

// TODO check for duplicate names?
// TODO improve the warnings
class AssignVariablesBuilder {
    private val assignments: MutableList<AssignVariable> = mutableListOf()

    infix fun String.ref(ref: String) {
        if (looksLikeSingleVariableDeref(ref)) {
            Logger.getGlobal().info(
                    "The Ref for AssignVariable[$this] looks like variable dereference." +
                    "Consider dropping the surrounding braces to reference the variable"
            )
        }
        assignments.add(AssignVariable(VariableType.Ref, this, ref))
    }

    infix fun String.value(ref: String) {
        if (looksLikeSingleVariableDeref(ref)) {
            Logger.getGlobal().info(
                    "The Value for AssignVariable[$this] looks like a variable dereference, but the " +
                    "Value node is for literal values. Use 'Ref' and remove the surrounding braces"
            )
        }
        assignments.add(AssignVariable(VariableType.Value, this, ref))
    }

    infix fun String.template(ref: String) {
        if (looksLikeSingleVariableDeref(ref)) {
            Logger.getGlobal().info(
                    "The Value for AssignVariable[$this] looks like a single variable dereference " +
                    "Use 'Ref' and remove the surrounding braces"
            )
        }
        AssignVariable(VariableType.Template, this, ref)
    }

    private fun looksLikeSingleVariableDeref(value: String): Boolean {
        return value.trim().let {
            it.startsWith('{') && it.endsWith('}') && it.count{ c -> "{}".contains(c) } == 2
        }
    }

    fun build(): List<AssignVariable> = assignments
}
