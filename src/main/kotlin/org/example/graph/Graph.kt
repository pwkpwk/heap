package org.example.graph

import java.util.*

class Graph(private val size: Int) {
    private val weights = arrayOfNulls<Int?>(size * size)

    operator fun get(x: Int, y: Int): Int? =
        if (x in 0 until size && y in 0 until size) {
            weights[x * size + y]
        } else {
            null
        }

    operator fun set(x: Int, y: Int, value: Int?) {
        if (x in 0 until size && y in 0 until size) {
            weights[x * size + y] = value
        }
    }

    fun recursiveShortestPath(start: Int, end: Int): List<Int> {
        val path = LinkedList<Int>()

        if (start in 0 until size && end in 0 until size && start != end) {
            val pathArray = Array<Int>(size) { -1 }
            val visited = Array(size) { false }

            rsp(start, end, visited, pathArray)?.let {
                var vertex = end
                do {
                    path.addFirst(vertex)
                    vertex = pathArray[vertex]
                } while (vertex != start)
                path.addFirst(start)
            }
        }

        return path
    }

    fun dijkstraShortestPath(start: Int, end: Int): Array<Int>? {
        if (start in 0 until size && end in 0 until size && start != end) {
            val unvisited = Array<Boolean>(size) { true }
            var unvisitedNumber = size
            val distance: Array<Int> = Array(size) { Int.MAX_VALUE }
            val trace: Array<Int> = Array(size) { -1 }
            distance[start] = 0

            while (unvisitedNumber > 0) {
                var minVertex: Int? = null
                var minDistance = Int.MAX_VALUE

                for (v in 0 until size) {
                    if (unvisited[v]) {
                        if (distance[v] < minDistance) {
                            minVertex = v
                            minDistance = distance[v]
                        }
                    }
                }

                if (minVertex != null) {
                    unvisited[minVertex] = false
                    --unvisitedNumber

                    if (minVertex == end) {
                        return LinkedList<Int>().let {
                            it.addFirst(minVertex)
                            while (minVertex != start) {
                                minVertex = trace[minVertex!!]
                                it.addFirst(minVertex)
                            }
                            it.toArray(Array<Int>(it.size) { 0 })
                        }
                    } else {
                        for (v in 0 until size) {
                            if (unvisited[v]) {
                                get(minVertex!!, v)?.let {
                                    val alt = distance[minVertex!!] + it
                                    if (alt < distance[v]) {
                                        distance[v] = alt
                                        trace[v] = minVertex!!
                                    }
                                }
                            }
                        }
                    }
                } else {
                    break
                }
            }
        }

        return null
    }

    private fun rsp(start: Int, end: Int, visited: Array<Boolean>, path: Array<Int>): Int? {
        if (start == end) {
            return 0
        }

        var minPath: Int? = null
        var minNeighbor: Int? = null

        visited[start] = true

        // Go through all unvisited neighbors and find the one with the shortest path to the end
        for (neighbor in 0 until size) {
            if (!visited[neighbor]) {
                val pathToNeighbor = get(start, neighbor);

                if (pathToNeighbor != null) {
                    // Find the path from the neighbor to the end and update the current minimum
                    val pathFromNeighbor = rsp(neighbor, end, visited, path)

                    if (pathFromNeighbor != null) {
                        val pathToEnd = pathToNeighbor + pathFromNeighbor

                        if (minPath == null || minPath > pathToEnd) {
                            minPath = pathToEnd
                            minNeighbor = neighbor
                        }
                    }
                }

            }
        }

        // If the minimum neighbor has been found, add a trace back from it to the start of the current search
        minNeighbor?.let { path[minNeighbor] = start }

        visited[start] = false

        return minPath
    }
}
