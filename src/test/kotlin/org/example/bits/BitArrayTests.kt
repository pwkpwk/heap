package org.example.bits

import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BitArrayTests {
    @Test
    fun indexWithinArray_set_setsBit() {
        val array = BitArray(100)

        assertFalse { array[99] }
        array[99] = true
        assertTrue { array[99] }
        for (i in 0..7) {
            assertFalse { array[i] }
            array[i] = true
            assertTrue { array[i] }
        }
    }

    @Test
    fun indexWithinArray_clear_clearsBit() {
        val array = BitArray(100)

        array[99] = true
        assertTrue { array[99] }
        array[99] = false
        assertFalse { array[99] }
        for (i in 0..7) {
            array[i] = true
            assertTrue { array[i] }
            array[i] = false
            assertFalse { array[i] }
        }
    }

    @Test
    fun indexOutsideArray_set_throws() {
        val array = BitArray(100)

        assertThrows<IndexOutOfBoundsException> {
            array[-1] = true
        }.let {
            assertEquals("Index -1 is out of range 0..99", it.message)
        }
        assertThrows<IndexOutOfBoundsException> {
            array[100] = true
        }.let {
            assertEquals("Index 100 is out of range 0..99", it.message)
        }
    }
}