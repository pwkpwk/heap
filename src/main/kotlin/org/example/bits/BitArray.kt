package org.example.bits

class BitArray(val length: Int) {
    @OptIn(ExperimentalUnsignedTypes::class)
    private val data = UByteArray((length + 7) / 8)

    @OptIn(ExperimentalUnsignedTypes::class)
    operator fun get(index: Int): Boolean {
        validateIndex(index)

        val byteIndex = index / 8
        val bitIndex = index % 8

        return bit[bitIndex].and(data[byteIndex]) != zero
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    operator fun set(index: Int, value: Boolean) {
        validateIndex(index)

        val byteIndex = index / 8
        val bitIndex = index % 8

        if (value) {
            data[byteIndex] = data[byteIndex].or(bit[bitIndex])
        } else {
            data[byteIndex] = data[byteIndex].and(inv[bitIndex])
        }
    }

    private fun validateIndex(index: Int) {
        if (index !in 0 until length)
            throw IndexOutOfBoundsException("Index $index is out of range 0..${length - 1}")
    }

    private companion object {
        const val zero: UByte = 0u
        val bit = Array(8) { 1.shl(it).toUByte() }
        val inv = Array(8) { bit[it].inv() }
    }
}