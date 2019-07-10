package com.djrollins.apigeekt.dsl.assignmessage

import com.djrollins.apigeekt.model.assignmessage.AssignVariable
import com.djrollins.apigeekt.model.assignmessage.VariableType
import java.util.logging.Logger

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
            it.startsWith('{') && it.endsWith('}') && it.count { c -> "{}".contains(c) } == 2
        }
    }

    fun build(): List<AssignVariable> = assignments
}
