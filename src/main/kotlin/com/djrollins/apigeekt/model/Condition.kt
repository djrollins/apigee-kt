package com.djrollins.apigeekt.model

enum class Operator(val str: String) {
    NOT("!"),
    EQUAL("="),
    NOT_EQUAL("!="),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_THAN_OR_EQUAL("<="),
    GREATER_THAN_OR_EQUAL(">="),
    EQUALS_CASE_INSENSITIVE(":="),
    AND("&&"),
    OR("||"),
    JAVA_REGEX("~~"),
    MATCHES("~"),
    LIKE_PATH("~/"),
    STARTS_WITH("=|")

}

sealed class Condition {
    object Always : Condition()

    data class Equal<T>(val property: Property<T>, val value: T) : Condition()
    data class NotEqual<T>(val property:Property<T>, val value: T) : Condition()
    data class LessThan<T>(val property: Property<T>, val value: T) : Condition()
    data class GreaterThan<T>(val property: Property<T>, val value: T) : Condition()
    data class LessThanOrEqual<T>(val property: Property<T>, val value: T) : Condition()
    data class GreaterThanOrEqual<T>(val property: Property<T>, val value: T) : Condition()
    data class EqualCaseInsensitive<T>(val property: Property<T>, val value: T) : Condition()

    data class JavaRegex(val property: Property<String>, val value: String) : Condition()
    data class Matches(val property: Property<String>, val value: String) : Condition()
    data class LikePath(val property: Property<String>, val value: String) : Condition()

    data class And(val lhs: Condition, val rhs: Condition) : Condition()
    data class Or(val lhs: Condition, val rhs: Condition) : Condition()
    data class Not(val condition: Condition): Condition()

    infix fun and(rhs: Condition): And = And(this, rhs)
    infix fun or(rhs: Condition): Or = Or(this, rhs)
    fun not(condition: Condition): Condition = Not(this)
}

