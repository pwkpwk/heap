package org.example.graph

import org.example.pq.PriorityQueue
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
            val pathArray = Array(size) { -1 }
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

    private class QueuedVertex(val index: Int, val distance: Int)

    fun d(start: Int, end: Int): List<Int> {
        val trace = Array(size) { -1 }
        val distance = Array(size) { Int.MAX_VALUE }

        if (start in 0 until size && end in 0 until size && start != end) {
            val pq = PriorityQueue<QueuedVertex>(size) { x, y -> x.distance < y.distance }
            distance[start] = 0
            pq.push(QueuedVertex(start, 0))

            do {
                pq.pop().let { min ->
                    for (i in 0 until size) {
                        get(min.index, i)?.let { rib ->
                            val newDistance = rib + distance[min.index]
                            if (newDistance < distance[i]) {
                                distance[i] = newDistance
                                trace[i] = min.index
                                pq.push(QueuedVertex(i, newDistance))
                            }
                        }
                    }
                }
            } while (pq.isNotEmpty)
        }

        return if (trace[end] >= 0) {
            LinkedList<Int>().apply {
                var vertex = end
                do {
                    addFirst(vertex)
                    vertex = trace[vertex]
                } while (vertex != start)
                addFirst(start)
            }
        } else {
            return emptyList()
        }
    }

    fun dijkstraShortestPath(start: Int, end: Int): Array<Int>? {
        if (start in 0 until size && end in 0 until size && start != end) {
            val unvisited = Array(size) { true }
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
                            it.toArray(Array(it.size) { 0 })
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

    private class Edge(val start: Int, val end: Int, val distance: Int)

    fun bellmanFordShortestPath(start: Int, end: Int): List<Int> {
        return LinkedList<Int>().also { list ->
            if (start in 0 until size && end in 0 until size && start != end) {
                val edges = mutableListOf<Edge>()

                for (destination in 0 until size) {
                    for (source in 0 until size) {
                        get(source, destination)?.let { edges.add(Edge(source, destination, it)) }
                    }
                }

                val distance = Array(size) { Int.MAX_VALUE }
                val trace = Array(size) { -1 }

                distance[start] = 0

                // Main loop of the algorithm - try to improve the destination distances of all vertices
                for (destination in 1 until size) {
                    for (edge in edges) {
                        if (distance[edge.start] != Int.MAX_VALUE && distance[edge.start] + edge.distance < distance[edge.end]) {
                            distance[edge.end] = distance[edge.start] + edge.distance
                            trace[edge.end] = edge.start
                        }
                    }
                }

                if (distance[end] != Int.MAX_VALUE) {
                    var index = end

                    do {
                        list.addFirst(index)
                        index = trace[index]
                    } while (index != start)
                    list.addFirst(start)
                }
            }
        }
    }

    private fun rsp(start: Int, end: Int, visited: Array<Boolean>, trace: Array<Int>): Int? {
        if (start == end) {
            return 0
        }

        var minPath: Int? = null
        var minNeighbor: Int? = null

        visited[start] = true

        // Go through all unvisited neighbors and find the one with the shortest path to the end
        for (neighbor in 0 until size) {
            if (!visited[neighbor]) {
                val distanceToNeighbor = get(start, neighbor)

                if (distanceToNeighbor != null) {
                    // Find the distance from the neighbor to the end and update the current minimum
                    val distanceFromNeighbor = rsp(neighbor, end, visited, trace)

                    if (distanceFromNeighbor != null) {
                        val pathToEnd = distanceToNeighbor + distanceFromNeighbor

                        if (minPath == null || minPath > pathToEnd) {
                            minPath = pathToEnd
                            minNeighbor = neighbor
                        }
                    }
                }

            }
        }

        // If the minimum neighbor has been found, add a trace back from it to the start of the current search
        minNeighbor?.let { trace[minNeighbor] = start }

        visited[start] = false

        return minPath
    }
}
