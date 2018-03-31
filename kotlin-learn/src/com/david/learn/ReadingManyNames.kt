package com.david.learn

/**
 * Line 9 demonstrates the for-loop, that would have been called "enhanced"
 * if there were any other for-loop in Kotlin.
 * See http://kotlinlang.org/docs/refrence/basic-syntax.html#using-a-for-loop
 */
fun main(args: Array<String>) {
    for (name in args) {
        println("hello, $name")
    }
}