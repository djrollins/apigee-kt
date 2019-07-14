package com.djrollins.apigeekt.dsl.assignmessage

import com.djrollins.apigeekt.common.Assignable
import com.djrollins.apigeekt.common.Template
import com.djrollins.apigeekt.common.Value
import com.djrollins.apigeekt.common.VariableReference
import com.djrollins.apigeekt.model.assignmessage.AssignVariable

// TODO check for duplicate names?
// TODO improve the warnings
class AssignVariablesBuilder {
    private val assignments: MutableList<AssignVariable<Assignable>> = mutableListOf()

    infix fun String.to(ref: VariableReference<Assignable>) {
        assignments.add(AssignVariable(this, ref))
    }

    infix fun <T> String.to(value: T) {
        assignments.add(AssignVariable(this, Value(value)))
    }

    infix fun String.to(template: Template) {
        assignments.add(AssignVariable(this, template))
    }

    fun build(): List<AssignVariable<Assignable>> = assignments
}
