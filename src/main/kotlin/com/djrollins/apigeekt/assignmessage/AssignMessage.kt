package com.djrollins.apigeekt.assignmessage

data class AssignMessage(
        val name: String,
        val enabled: Boolean,
        val continueOnError: Boolean,
        val ignoreUnresolvedVariables: Boolean,
        val assignTo: AssignTo?,
        val assignVariables: List<AssignVariable>,
        val add: Add,
        val copy: Copy,
        val remove: Any?,
        val set: Any?
)
