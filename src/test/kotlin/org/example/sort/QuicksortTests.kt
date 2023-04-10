package org.example.sort

import kotlin.test.Test
import kotlin.test.assertTrue

class QuicksortTests {
    @Test
    fun array_sort_sorted() {
        arrayOf(5, 4, 3, 2, 1, 6, 7, 8, 10, 9).let { sequence ->
            Quicksort(sequence) { x, y -> x < y }.quicksort()

            for (i in 2 until sequence.size) {
                assertTrue { sequence[i - 1] < sequence[i] }
            }
        }
    }

    @Test
    fun list_sort_sorted() {
        mutableListOf(5, 4, 3, 2, 1, 6, 7, 8, 10, 9).let { sequence ->
            Quicksort(sequence) { x, y -> x < y }.quicksort()

            for (i in 2 until sequence.size) {
                assertTrue { sequence[i - 1] < sequence[i] }
            }
        }
    }
}

