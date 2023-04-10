package org.example.tools

fun interface Equality<T> {
    /**
     * Check two elements of an indexed collection for equality
     *
     * @param indexX Index of the first element
     * @param indexY Index of the second element
     * @return True if the elements with the specified indexes are equal; otherwise, false
     */
    fun areEqual(indexX: T, indexY: T): Boolean
}
