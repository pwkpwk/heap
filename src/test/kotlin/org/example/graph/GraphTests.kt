package org.example.graph

import org.junit.jupiter.api.Assertions.assertIterableEquals
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GraphTests {

    @Test
    fun negaticeWeight_recursiveShortestPath_findsPath() {
        val graph = Graph(5);

        graph[0, 1] = 10
        graph[0, 2] = 3
        graph[0, 3] = 1
        graph[1, 4] = -10
        graph[3, 2] = 1
        graph[3, 3] = 2
        graph[3, 4] = 3
        graph[2, 4] = 1
        graph[2, 1] = 3

        graph.recursiveShortestPath(0, 4).let {
            assertEquals(5, it.size)
            assertIterableEquals(listOf(0, 3, 2, 1, 4), it)
        }
    }

    @Test
    fun pathExists_dijkstraShortestPath_findsPath() {
        val graph = Graph(10);
        graph[0, 1] = 1
        graph[0, 5] = 1
        graph[1, 2] = 1
        graph[2, 3] = 1
        graph[3, 4] = 1
        graph[4, 5] = 1
        graph[5, 6] = 1
        graph[6, 7] = 1
        graph[6, 8] = 5
        graph[7, 8] = 1
        graph[8, 9] = 1

        graph.dijkstraShortestPath(0, 8)!!.let {
            assertEquals(5, it.size)
            assertIterableEquals(listOf(0, 5, 6, 7, 8), it.asIterable())
        }
    }

    @Test
    fun noPath_dijkstraShortestPath_returnsNull() {
        val graph = Graph(10);
        graph[0, 1] = 1
        graph[0, 5] = 1
        graph[1, 2] = 1
        graph[2, 3] = 1
        graph[3, 4] = 1
        graph[7, 8] = 1
        graph[8, 9] = 1

        assertNull(graph.dijkstraShortestPath(0, 8))
    }
}
