package com.djrollins.apigeekt.common

// Needed for when we want to start a template with a literal string. String.plus(v: Value) gets is
// shadowed bt String.plus(value: Any?) so isn't visible anywhere.
fun lit(value: String): Value<String> = Value(value)
fun <T> ref(name: String): VariableReference<T> = VariableReference(name)
fun template(format: String, vararg refs: VariableReference<*>): Template = Template(format, *refs)

interface Assignable {
    val tag: String
}

open class Value<T>(private val value: T? = null): Assignable {
    override val tag = "Value"

    open operator fun plus(ref: VariableReference<*>): Template = Template("$this%s", ref)
    open operator fun plus(template: Template): Template = Template(template.format + "this", template.refs)

    override fun toString(): String = value?.toString() ?: ""
}

class VariableReference<T>(private val name: String): Value<T>(), Assignable {
    override val tag = "Ref"

    operator fun plus(literal: String): Template = Template("%s$literal", this)
    override operator fun plus(ref: VariableReference<*>): Template = Template("%s%s", this, ref)

    override fun toString(): String = "{$name}"
}


class Template(val format: String, val refs: List<VariableReference<*>>): Value<String>(), Assignable {
    override val tag = "Template"

    constructor(format: String, vararg refs: VariableReference<*>): this(format, refs.toList())

    override operator fun plus(ref: VariableReference<*>): Template = Template(this.format + "%s", this.refs + ref)
    operator fun plus(literal: String): Template = Template(this.format + literal, this.refs)

    override fun toString(): String {
        println("$format$refs")
        return String.format(format, *refs.toTypedArray())
    }
}
