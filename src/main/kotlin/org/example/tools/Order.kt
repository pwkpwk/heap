package org.example.tools

fun interface Order<T> {
    fun areInOrder(x: T, y: T): Boolean
}
