package com.david.learn

/**
 * Line 14 demonstrates string templates and array access.
 * See this pages for detail:
 * http://kotlinlang.org/docs/refrence/basic-types.html#strings
 * http://kotlinlang.org/docs/refrence/basic-types.html#arrays
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Please provide a name as a command-line argument")
        return
    }
    println("Hello, ${args[0]}!")
}