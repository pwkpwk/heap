package org.example.heap

import kotlin.test.Test
import kotlin.test.assertTrue

class HeapTests {
    @Test
    fun array_heapsort_sortsData() {
        arrayOf(5, 4, 3, 1, 2, 7, 6, 8, 9).let { sequence ->
            Heap(sequence) { x, y -> x > y }.heapsort()

            for (i in 2 until sequence.size) {
                assertTrue { sequence[i - 1] < sequence[i] }
            }
        }
    }

    @Test
    fun list_heapsort_sortsData() {
        mutableListOf(5, 4, 3, 1, 2, 7, 6, 8, 9).let { sequence ->
            Heap(sequence) { x, y -> x > y }.heapsort()

            for (i in 2 until sequence.size) {
                assertTrue { sequence[i - 1] < sequence[i] }
            }
        }
    }

    @Test
    fun array_build_correctHeap() {
        arrayOf(5, 4, 3, 1, 2, 7, 6, 8, 9).let { sequence ->
            Heap(sequence) { x, y -> x < y }.build(sequence.size)

            for (i in 2 until sequence.size) {
                assertTrue { sequence[i] > sequence[(i + 1) / 2 - 1] }
            }
        }
    }

    @Test
    fun list_build_correctHeap() {
        mutableListOf(5, 4, 3, 1, 2, 7, 6, 8, 9).let { sequence ->
            Heap(sequence) { x, y -> x < y }.build(sequence.size)

            for (i in 2 until sequence.size) {
                assertTrue { sequence[i] > sequence[(i + 1) / 2 - 1] }
            }
        }
    }
}
