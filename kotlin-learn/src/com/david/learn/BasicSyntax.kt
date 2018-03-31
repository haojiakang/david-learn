package com.david.learn

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum2(a: Int, b: Int) = a + b

fun printSum(a: Int, b: Int): Unit {
    println("sum of $a and $b is ${a + b}")
}

fun printSum2(a: Int, b: Int) {
    println("sum of $a and $b is ${a + b}")
}

fun test() {
    val a: Int = 1
    val b = 2
    val c: Int
    c = 3
}

val PI = 3.14
var x = 0

fun incrementX() {
    x += 1
}

var a = 1
var s1 = "a is $a"

fun maxOf(a: Int, b: Int): Int {
    if (a > b) {
        return a
    }
    return b
}

fun maxOf2(a: Int, b: Int): Int = if (a > b) a else b

fun getStringLength(obj: Any): Int? {
    if (obj is String) {
        return obj.length
    }
    return null
}

fun getString(obj: Any): String {
    if (obj is String) {
        return obj.toString()
    }
    return null.toString()
}

fun forLoop() {
    val items = listOf("apple", "banana", "kiwifruit")
    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }
}

fun whileLoop() {
    val items = listOf("apple", "banana", "kiwifruit")
    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }
}

fun describe(obj: Any): String =
        when (obj) {
            1 -> "One"
            "Hello" -> "Greeting"
            is Long -> "Long"
            !is String -> "Not a string"
            else -> "Unknown"
        }

fun ranges() {
    var x = 10
    var y = 9
    if (x in 1..y + 1) {
        println("fits in range")
    }
}

fun checkRange() {
    var list = listOf("a", "b", "c")
    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range too")
    }
}

fun iterating() {
    for (x in 1..5) {
        println(x)
    }
}

fun streamOps() {
    var fruits = listOf("apple", "banana", "pear")
    fruits.filter { it.startsWith("a") }.sortedBy { it }.map { it.toUpperCase() }.forEach{ println(it) }
}

fun main(args: Array<String>) {
    streamOps()
}
