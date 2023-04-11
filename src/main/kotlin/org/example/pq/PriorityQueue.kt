package org.example.pq

import org.example.heap.Heap
import org.example.tools.Order

class PriorityQueue<T> {

    private val data: MutableList<T>
    private val heap: Heap<T>

    constructor(order: Order<T>) {
        data = mutableListOf()
        heap = Heap(data, order)
    }

    constructor(capacity: Int, order: Order<T>) {
        data = ArrayList(capacity)
        heap = Heap(data, order)
    }

    val isEmpty get() = data.isEmpty()

    val isNotEmpty get() = data.isNotEmpty()

    /**
     * Push a value onto the priority queue and update the queue
     */
    fun push(value: T) {
        data.add(value)
        heap.siftUp(data.size)
    }

    /**
     * Pop the top value from the queue and update the queue
     */
    fun pop(): T {
        if (data.isEmpty())
            throw UnsupportedOperationException("Cannot pop the top from an empty priority queue")

        return data[0].also {
            if (data.size == 1) {
                data.clear()
            } else {
                data[0] = data.removeLast()
                heap.siftDown(data.size)
            }
        }
    }
}