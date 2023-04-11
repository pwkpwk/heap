package org.example.heap

import org.example.tools.DirectAccess
import org.example.tools.Order
import kotlin.math.min

class Heap<T> {
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

    fun build(size: Int) {
        data.length.let { length ->
            if (length > 1) {
                for (i in 2..min(size, length)) {
                    siftUp(i)
                }
            }
        }
    }

    /**
     * Sort the underlying data using the Heapsort algorithm.
     */
    fun heapsort() {
        build(data.length)
        for (i in data.length downTo 2) {
            data.swap(1, i)
            siftDown(i - 1)
        }
    }

    /**
     * Sift the top element (with one-based index of 1) down to the specified bottom index
     */
    fun siftDown(oneBaseBottomIndex: Int) {
        var index = 1

        while (index < oneBaseBottomIndex) {
            var child = index * 2

            index = if (child <= oneBaseBottomIndex) {
                if (child < oneBaseBottomIndex && areElementsInOrder(child + 1, child)) {
                    ++child
                }

                // child is the lesser child of index
                if (!areElementsInOrder(child, index)) {
                    oneBaseBottomIndex
                } else {
                    data.swap(child, index)
                    child
                }
            } else {
                oneBaseBottomIndex
            }
        }
    }

    /**
     * Sift the specified element up the heap
     */
    fun siftUp(oneBaseIndex: Int) {
        var index = oneBaseIndex

        while (index > 1) {
            index = (index / 2).let { parent ->
                if (!areElementsInOrder(parent, index)) {
                    data.swap(index, parent)
                    parent
                } else {
                    1
                }
            }
        }
    }

    private fun areElementsInOrder(x: Int, y: Int) = order.areInOrder(data[x], data[y])

    private class ArrayDirectAccess<T>(private val data: Array<T>) : DirectAccess<T> {
        override val length: Int = data.size

        override fun get(index: Int): T = data[index - 1]

        override fun set(index: Int, value: T) {
            data[index - 1] = value
        }
    }

    private class ListDirectAccess<T>(private val data: MutableList<T>) : DirectAccess<T> {
        override val length: Int get() = data.size

        override fun get(index: Int): T = data[index - 1]

        override fun set(index: Int, value: T) {
            data[index - 1] = value
        }
    }
}
