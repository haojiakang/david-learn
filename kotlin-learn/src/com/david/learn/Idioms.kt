package com.david.learn

data class Customer(val name: String, val email: String)

fun foo(a: Int = 0, b: String = "") {

}

val positives = listOf(1).filter { x -> x > 0 }
val positives2 = listOf(2).filter { it > 0 }

fun main(args: Array<String>) {
    val customer = Customer("Jack", "slkd@kls.com")
    val str = customer.toString()
    println(str)
}