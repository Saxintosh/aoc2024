package y2024

import java.lang.Math.pow
import kotlin.math.pow

fun <T> List<T>.allDistinctPairs(): List<Pair<T, T>> {
    val pairs = arrayListOf<Pair<T, T>>()
    for (i in this.indices) {
        for (j in i + 1 until this.size) {
            pairs.add(this[i] to this[j])
        }
    }
    return pairs
}

//fun <T> List<T>.allDistinctPairs(): List<Pair<T, T>> {
//    return this.indices.flatMap { i ->
//        (i + 1 until this.size).map { j -> Pair(this[i], this[j]) }
//    }
//}
