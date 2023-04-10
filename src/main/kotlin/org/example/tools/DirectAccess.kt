package org.example.tools

/**
 * Wrapper of collections with direct access to elements that also implements
 * one-based indexing of the collection, which is important for the heap index
 * calculations.
 */
interface DirectAccess<T> {
    val length: Int

    operator fun get(index: Int): T

    operator fun set(index: Int, value: T)

    fun swap(x: Int, y: Int) {
        get(x).let {
            set(x, get(y))
            set(y, it)
        }
    }
}
