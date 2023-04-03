package org.example.heap

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
            if (length >= 2) {
                for (i in 2..if (size > length) length else size) {
                    siftUp(i)
                }
            }
        }
    }

    fun sort() {
        build(data.length)
        for (i in data.length downTo 2) {
            data.swap(1, i)
            siftDown(i - 1)
        }
    }

    fun binarySearch(value: T, equality: Equality<T>): Int {
        var l = 0
        var u = data.length + 1
        var index = -1

        while (l + 1 < u) {
            ((l + u) / 2).let {
                if (order.areInOrder(data.get(it), value)) { // value <= mv
                    l = it
                } else { // value > mv
                    u = it
                }
            }
        }

        if (u <= data.length && equality.areEqual(value, data.get(u))) {
            index = u - 1
        }

        return index
    }

    /**
     * Sift the top element (with one-based index of 1) down to the specified bottom index
     */
    fun siftDown(oneBaseBottomIndex: Int) {
        var index = 1

        while (index < oneBaseBottomIndex) {
            var child = index * 2

            index = if (child <= oneBaseBottomIndex) {
                if (child < oneBaseBottomIndex && data.inOrder(child + 1, child, order)) {
                    ++child
                }
                // child is the lesser child of index
                if (!data.inOrder(child, index, order)) {
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

    fun interface Order<T> {
        fun areInOrder(x: T, y: T): Boolean
    }

    fun interface Equality<T> {
        fun areEqual(x: T, y: T): Boolean
    }

    /**
     * Sift the specified element up the heap
     */
    private fun siftUp(oneBaseIndex: Int) {
        var index = oneBaseIndex

        while (index > 1) {
            index = (index / 2).let { parent ->
                if (!data.inOrder(parent, index, order)) {
                    data.swap(index, parent)
                    parent
                } else {
                    1
                }
            }
        }
    }

    /**
     * Wrapper of collections with direct access to elements that also implements
     * one-based indexing of the collection, which is important for the heap index
     * calculations.
     */
    private interface DirectAccess<T> {
        val length: Int

        fun get(oneBaseIndex: Int): T

        fun put(oneBaseIndex: Int, value: T)

        fun inOrder(x: Int, y: Int, order: Order<T>): Boolean = order.areInOrder(get(x), get(y))

        fun swap(x: Int, y: Int) {
            get(x).let {
                put(x, get(y))
                put(y, it)
            }
        }
    }

    private class ArrayDirectAccess<T>(private val data: Array<T>) : DirectAccess<T> {
        override val length: Int = data.size

        override fun get(oneBaseIndex: Int): T = data[oneBaseIndex - 1]

        override fun put(oneBaseIndex: Int, value: T) {
            data[oneBaseIndex - 1] = value
        }
    }

    private class ListDirectAccess<T>(private val data: MutableList<T>) : DirectAccess<T> {
        override val length: Int get() = data.size

        override fun get(oneBaseIndex: Int): T = data[oneBaseIndex - 1]

        override fun put(oneBaseIndex: Int, value: T) {
            data[oneBaseIndex - 1] = value
        }
    }
}