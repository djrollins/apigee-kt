package com.djrollins.apigeekt.model

import com.djrollins.apigeekt.model.Condition.*

data class NameValuePair(val name: String, val value: String)

class Property<T>(val identifier: String) {
    infix fun eq(rhs: T): Equal<T> = this equal rhs
    infix fun ne(rhs: T): NotEqual<T> = this notEqual rhs
    infix fun lt(rhs: T): LessThan<T> = this lessThan rhs
    infix fun gt(rhs: T): GreaterThan<T> = this greaterThan rhs
    infix fun lte(rhs: T): LessThanOrEqual<T> = this lessThanOrEqual rhs
    infix fun gte(rhs: T): GreaterThanOrEqual<T> = this greaterThanOrEqual rhs

    infix fun equal(rhs: T): Equal<T> = Equal(this, rhs)
    infix fun notEqual(rhs: T): NotEqual<T> = NotEqual(this, rhs)
    infix fun lessThan(rhs: T): LessThan<T> = LessThan(this, rhs)
    infix fun greaterThan(rhs: T): GreaterThan<T> = GreaterThan(this, rhs)
    infix fun lessThanOrEqual(rhs: T): LessThanOrEqual<T> = LessThanOrEqual(this, rhs)
    infix fun greaterThanOrEqual(rhs: T): GreaterThanOrEqual<T> = GreaterThanOrEqual(this, rhs)
    infix fun equalCaseInsensitive(rhs: T): EqualCaseInsensitive<T> = EqualCaseInsensitive(this, rhs)
}

fun Property<String>.javaRegex(rhs: String): JavaRegex = JavaRegex(this, rhs)
fun Property<String>.matches(rhs: String): Matches = Matches(this, rhs)
fun Property<String>.likePath(rhs: String): LikePath = LikePath(this, rhs)
