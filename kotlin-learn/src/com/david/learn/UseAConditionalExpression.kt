package com.david.learn

/**
 * `if` is an expression, i.e. it returns a value.
 * Therefore there is no ternary operator (condition ? then : else),
 * because ordinary `if` works fine in this role.
 * See http://kotlinlang.org/docs/refrence/control-flow.html#if-expression
 */
fun main(args: Array<String>) {
    val a = 1
    val b = 2
    println(max(a, b))
}

fun max(a: Int, b: Int) = if (a > b) a else b
