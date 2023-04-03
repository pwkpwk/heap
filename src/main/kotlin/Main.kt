import org.example.heap.Heap

fun main(args: Array<String>) {
    // Implement merge sorting of a collection of sequences
    val tapes = mutableListOf(
        mutableListOf(90, 80, 70, 60, 50, 40, 30, 20, 10),
        mutableListOf(105, 115, 125, 135, 95, 85, 75, 65, 55, 45, 35, 25, 15),
        mutableListOf(9, 87, 7, 6, 5, 4, 3, 2, 1, 11, 12),
    )
    val merged = mutableListOf<Int>()

    // Sort individual tapes
    for (tape in tapes) {
        // The order is reversed from greater to smaller values because the heap sort algorithm
        // picks the lowest element in the order from the top of the heap and pushes it to the end
        // of the sequence, so the sorted data grows from the end towards the beginning of the data
        Heap(tape) { x, y -> x > y }.sort()
    }

    val iterators = mutableListOf<ListIterator<Int>>().apply { addAll(tapes.map { it.listIterator() }) }

    Heap(iterators) { x, y ->
        val xv = x.next()
        val yv = y.next()
        x.previous()
        y.previous()
        xv < yv
    }.let { tapesHeap ->
        tapesHeap.build(iterators.size)

        while (iterators.isNotEmpty()) {
            // Remove the top element from the top tape into the merged list
            iterators[0].let {
                merged.add(it.next())

                if (!it.hasNext()) {
                    // Replace the top tape with the last tape and sift it down
                    iterators[0] = iterators[iterators.size - 1]
                    iterators.removeAt(iterators.size - 1)
                }

                if (iterators.size > 1) {
                    tapesHeap.siftDown(iterators.size)
                } else {
                    while (iterators[0].hasNext()) {
                        merged.add(iterators[0].next())
                    }
                    iterators.clear()
                }
            }
        }
    }
}