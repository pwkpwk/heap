import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.api.RetrofitInstance
import org.example.heap.Heap
import retrofit2.await
import java.util.concurrent.CountDownLatch

fun main() {
    val rxScheduler = Schedulers.computation()
    val exitGate = CountDownLatch(3)

    runBlocking {
        println("Kotlin")
        // Implement merge sorting of a collection of sequences
        val tapes = mutableListOf(
            mutableListOf(90, 80, 70, 60, 50, 40, 30, 20, 10, 104, 107),
            mutableListOf(105, 115, 125, 135, 95, 85, 75, 65, 55, 45, 35, 25, 15),
            mutableListOf(9, 87, 7, 6, 5, 4, 3, 2, 1, 11, 12, 103, 106),
        )
        val merged = mutableListOf<Int>()

        // Sort individual tapes
        for (tape in tapes) {
            // The order is reversed from greater to smaller values because the heap sort algorithm
            // picks the lowest element in the order from the top of the heap and pushes it to the end
            // of the sequence, so the sorted data grows from the end towards the beginning of the data
            Heap(tape) { x, y -> x > y }.heapsort()
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

        // A little bit of RxKotlin
        listOf(1, 2, 3, 4, 5).toObservable().subscribeBy(
            onNext = { println(it) },
            onError = { println(it) },
            onComplete = { println("finished") }
        )

        val disposable: Disposable = RetrofitInstance.blogApi.getPostsObservable()
            .subscribeOn(rxScheduler)
            .observeOn(rxScheduler)
            .subscribeBy(
                onNext = { println("Length: ${it.size}") },
                onError = { println("FAILED RX: ${it.message}") },
                onComplete = {
                    println("Complete")
                    exitGate.countDown()
                }
            )
        // disposable.dispose()
        // exitGate.countDown()

        val call = RetrofitInstance.blogApi.getPostsCall()

        try {
            val cr = launch {
                try {
                    println("POSTS: ${call.await().size}")
                } catch (ex: Throwable) {
                    println("FAILED INNER: ${ex.message}")
                } finally {
                    exitGate.countDown()
                }
            }
            call.cancel()
            cr.join()
        } catch (ex: Throwable) {
            println("FAILED: ${ex.message}")
        } finally {
            exitGate.countDown()
        }
    }

    exitGate.await()
    println("The gate has opened")
    RetrofitInstance.shutdown()
    rxScheduler.shutdown()
}