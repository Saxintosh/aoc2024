package y2024

fun <T> List<T>.getOrderedPairs(): List<Pair<T, T>> {
    val pairs = mutableListOf<Pair<T, T>>()
    for (i in this.indices) {
        for (j in i + 1 until this.size) {
            pairs.add(this[i] to this[j])
        }
    }
    return pairs
}

fun <T> List<T>.cartesianProduct(n: Int): Sequence<List<T>> = sequence {
    val indices = IntArray(n)
    val size = this@cartesianProduct.size

    // Generate combinations until all possibilities are exhausted
    while (true) {
        // Yield the current combination based on the indices
        yield(indices.map { this@cartesianProduct[it] })

        // Find the rightmost index to increment
        var i = n - 1
        while (i >= 0 && indices[i] == size - 1) {
            indices[i] = 0
            i--
        }

        // If all indices are at their maximum value, we have exhausted all combinations
        if (i < 0) break

        indices[i]++
    }
}