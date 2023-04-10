package org.example.search

import org.example.tools.DirectAccess
import org.example.tools.Equality
import org.example.tools.Order

class Search<T> {
    private val data: DirectAccess<T>
    private val order: Order<T>

    constructor(data: Array<T>, order: Order<T>) {
        this.data = ArrayDirectAccess(data)
        this.order = order
    }

    constructor(data: List<T>, order: Order<T>) {
        this.data = ListDirectAccess(data)
        this.order = order
    }

    fun binarySearch(value: T, equality: Equality<T>): Int {
        var l = -1
        var u = data.length
        var index = -1

        while (u - l > 1) {
            ((l + u) / 2).let {
                if (order.areInOrder(data[it], value)) { // value <= mv
                    l = it
                } else { // value > mv
                    u = it
                }
            }
        }

        if (u < data.length && equality.areEqual(value, data[u])) {
            index = u
        }

        return index
    }

    private class ArrayDirectAccess<T>(private val data: Array<T>) : DirectAccess<T> {
        override val length: Int = data.size

        override fun get(index: Int): T = data[index]

        override fun set(index: Int, value: T) {
            data[index] = value
        }
    }

    private class ListDirectAccess<T>(private val data: List<T>) : DirectAccess<T> {
        override val length: Int get() = data.size

        override fun get(index: Int): T = data[index]

        override fun set(index: Int, value: T) {
            throw UnsupportedOperationException("Cannot modify the list")
        }
    }
}
