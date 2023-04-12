package org.example.pq

import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PriorityQueueTests {
    @Test
    fun emptyQueue_pop_throws() {
        val pq = PriorityQueue<Int> { x, y -> x < y }

        assertTrue { pq.isEmpty }
        assertFalse { pq.isNotEmpty }
        assertThrows<UnsupportedOperationException> {
            pq.pop()
        }
    }

    @Test
    fun addValues_popAll_correctOrder() {
        PriorityQueue<Int> { x, y -> x < y }.apply {
            push(5)
            push(3)
            push(4)
            push(2)
            push(1)

            assertEquals(1, pop())
            assertEquals(2, pop())
            assertEquals(3, pop())
            assertEquals(4, pop())
            assertEquals(5, pop())
            assertTrue { isEmpty }
            assertFalse { isNotEmpty }
        }
    }

    @Test
    fun initializedWithCapacity_popAll_correctOrder() {
        PriorityQueue<Int>(16) { x, y -> x < y }.apply {
            push(5)
            push(3)
            push(4)
            push(2)
            push(1)

            assertEquals(1, pop())
            assertEquals(2, pop())
            assertEquals(3, pop())
            assertEquals(4, pop())
            assertEquals(5, pop())
            assertTrue { isEmpty }
            assertFalse { isNotEmpty }
        }
    }
}