package org.example.heap

fun interface Order<T> {
    fun areInOrder(x: T, y: T): Boolean
}
