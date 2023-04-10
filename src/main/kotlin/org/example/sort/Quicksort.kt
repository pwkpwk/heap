package org.example.sort

import org.example.tools.DirectAccess
import org.example.tools.Order

class Quicksort<T> {
    private val data: DirectAccess<T>
    private val order: Order<T>

    constructor(data: Array<T>, order: Order<T>) {
        this.data = ArrayDirectAccess(data)
        this.order = order
    }

    constructor(data: MutableList<T>, order: Order<T>) {
        this.data = ListDirectAccess(data)
        this.order = order
    }

    /**
     * Sort the underlying data using the Quicksort algorithm
     */
    fun quicksort() {
        quicksort(0, data.length - 1)
    }

    private fun quicksort(l: Int, u: Int) {
        if (l < u) {
            val m = partition(l, u)

            quicksort(l, m - 1)
            quicksort(m + 1, u)
        }
    }

    private fun partition(l: Int, u: Int): Int {
        var m = l

        for (i in l + 1..u) {
            if (order.areInOrder(data[i], data[m])) {
                data.swap(m++, i)
            }
        }

        return m
    }

    private class ArrayDirectAccess<T>(private val data: Array<T>) : DirectAccess<T> {
        override val length: Int = data.size

        override fun get(index: Int): T = data[index]

        override fun set(index: Int, value: T) {
            data[index] = value
        }
    }

    private class ListDirectAccess<T>(private val data: MutableList<T>) : DirectAccess<T> {
        override val length: Int get() = data.size

        override fun get(index: Int): T = data[index]

        override fun set(index: Int, value: T) {
            data[index] = value
        }
    }
}
