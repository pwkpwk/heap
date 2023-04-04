package org.example.heap

import kotlin.test.Test
import kotlin.test.assertEquals

class SearchTests {
    @Test
    fun binarySearch_valueExists_findsIndex() {
        val array = arrayOf(1, 2, 3, 4, 5)

        assertEquals(4, Search(array) { x, y -> x < y }.binarySearch(5) { x, y -> x == y })
        assertEquals(3, Search(array) { x, y -> x < y }.binarySearch(4) { x, y -> x == y })
        assertEquals(2, Search(array) { x, y -> x < y }.binarySearch(3) { x, y -> x == y })
        assertEquals(1, Search(array) { x, y -> x < y }.binarySearch(2) { x, y -> x == y })
        assertEquals(0, Search(array) { x, y -> x < y }.binarySearch(1) { x, y -> x == y })
    }

    @Test
    fun binarySearch_duplicateValueExists_findsFirstIndex() {
        val array = arrayOf(1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 5)

        assertEquals(9, Search(array) { x, y -> x < y }.binarySearch(5) { x, y -> x == y })
        assertEquals(7, Search(array) { x, y -> x < y }.binarySearch(4) { x, y -> x == y })
        assertEquals(5, Search(array) { x, y -> x < y }.binarySearch(3) { x, y -> x == y })
        assertEquals(2, Search(array) { x, y -> x < y }.binarySearch(2) { x, y -> x == y })
        assertEquals(0, Search(array) { x, y -> x < y }.binarySearch(1) { x, y -> x == y })
    }

    @Test
    fun binarySearch_valueMissing_returnsError() {
        val array = arrayOf(10, 20, 30, 40, 50)

        assertEquals(-1, Search(array) { x, y -> x < y }.binarySearch(5) { x, y -> x == y })
        assertEquals(-1, Search(array) { x, y -> x < y }.binarySearch(15) { x, y -> x == y })
        assertEquals(-1, Search(array) { x, y -> x < y }.binarySearch(45) { x, y -> x == y })
        assertEquals(-1, Search(array) { x, y -> x < y }.binarySearch(55) { x, y -> x == y })
    }
}