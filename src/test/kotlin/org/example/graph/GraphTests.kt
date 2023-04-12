package org.example.graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GraphTests {

    @Test
    fun plop() {
        Graph(10).let {
            it[0, 1] = 1
            it[0, 5] = 1
            it[1, 2] = 1
            it[3, 4] = 1
            it[4, 5] = 1
            it[5, 6] = 1
            it[6, 7] = 1
            it[6, 8] = 5
            it[7, 8] = 1
            it[8, 9] = 1

            assertIterableEquals(listOf(0, 5, 6, 7, 8), it.d(0, 8))
            assertIterableEquals(listOf(0, 5, 6, 7, 8, 9), it.d(0, 9))
            assertTrue { it.d(0, 3).isEmpty() }
        }
    }

    @Test
    fun bf() {
        Graph(10).let {
            it[0, 1] = 1
            it[0, 5] = 1
            it[1, 2] = 1
            it[3, 4] = 1
            it[4, 5] = 1
            it[5, 6] = 1
            it[6, 7] = 1
            it[6, 8] = 5
            it[7, 8] = 1
            it[8, 9] = 1

            assertIterableEquals(listOf(0, 5, 6, 7, 8), it.bellmanFordShortestPath(0, 8))
            //assertIterableEquals(listOf(0, 5, 6, 7, 8, 9), it.bellmanFordShortestPath(0, 9))
            //assertTrue { it.bellmanFordShortestPath(0, 3).isEmpty() }
        }
    }

    @Test
    fun negativeWeight_recursiveShortestPath_findsPath() {
        Graph(5).let {
            it[0, 1] = 10
            it[0, 2] = 3
            it[0, 3] = 1
            it[1, 4] = -10
            it[3, 2] = 1
            it[3, 3] = 2
            it[3, 4] = 3
            it[2, 4] = 1
            it[2, 1] = 3

            assertIterableEquals(listOf(0, 3, 2, 1, 4), it.recursiveShortestPath(0, 4))
        }
    }

    @Test
    fun pathExists_dijkstraShortestPath_findsPath() {
        Graph(10).let {
            it[0, 1] = 1
            it[0, 5] = 1
            it[1, 2] = 1
            it[2, 3] = 1
            it[3, 4] = 1
            it[4, 5] = 1
            it[5, 6] = 1
            it[6, 7] = 1
            it[6, 8] = 5
            it[7, 8] = 1
            it[8, 9] = 1

            assertIterableEquals(listOf(0, 5, 6, 7, 8), it.dijkstraShortestPath(0, 8)!!.asIterable())
        }
    }

    @Test
    fun noPath_dijkstraShortestPath_returnsNull() {
        Graph(10).let {
            it[0, 1] = 1
            it[0, 5] = 1
            it[1, 2] = 1
            it[2, 3] = 1
            it[3, 4] = 1
            it[7, 8] = 1
            it[8, 9] = 1

            assertNull(it.dijkstraShortestPath(0, 8))
        }
    }
}
