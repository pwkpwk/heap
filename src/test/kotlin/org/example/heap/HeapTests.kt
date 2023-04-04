package org.example.heap

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HeapTests {
    @Test
    fun binarySearch_valueExists_findsIndex() {
        val array = arrayOf(1, 2, 3, 4, 5)

        assertEquals(4, Heap(array) { x, y -> x < y }.binarySearch(5) { x, y -> x == y })
        assertEquals(3, Heap(array) { x, y -> x < y }.binarySearch(4) { x, y -> x == y })
        assertEquals(2, Heap(array) { x, y -> x < y }.binarySearch(3) { x, y -> x == y })
        assertEquals(1, Heap(array) { x, y -> x < y }.binarySearch(2) { x, y -> x == y })
        assertEquals(0, Heap(array) { x, y -> x < y }.binarySearch(1) { x, y -> x == y })
    }

    @Test
    fun binarySearch_duplicateValueExists_findsFirstIndex() {
        val array = arrayOf(1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 5)

        assertEquals(9, Heap(array) { x, y -> x < y }.binarySearch(5) { x, y -> x == y })
        assertEquals(7, Heap(array) { x, y -> x < y }.binarySearch(4) { x, y -> x == y })
        assertEquals(5, Heap(array) { x, y -> x < y }.binarySearch(3) { x, y -> x == y })
        assertEquals(2, Heap(array) { x, y -> x < y }.binarySearch(2) { x, y -> x == y })
        assertEquals(0, Heap(array) { x, y -> x < y }.binarySearch(1) { x, y -> x == y })
    }

    @Test
    fun binarySearch_valueMissing_returnsError() {
        val array = arrayOf(10, 20, 30, 40, 50)

        assertEquals(-1, Heap(array) { x, y -> x < y }.binarySearch(5) { x, y -> x == y })
        assertEquals(-1, Heap(array) { x, y -> x < y }.binarySearch(15) { x, y -> x == y })
        assertEquals(-1, Heap(array) { x, y -> x < y }.binarySearch(45) { x, y -> x == y })
        assertEquals(-1, Heap(array) { x, y -> x < y }.binarySearch(55) { x, y -> x == y })
    }

    @Test
    fun qs() {
        val array = arrayOf(5, 4, 3, 2, 1, 6, 7, 8, 10, 9)

        Heap(array) { x, y -> x < y }.quicksort()
        for (i in 1..array.size - 1) {
            assertTrue { array[i - 1] < array[i] }
        }
    }
}