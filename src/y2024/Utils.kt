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

fun findPathsFromPoint(
    grid: ChGrid,
    startPoint: Point,
    isEndCondition: (Point) -> Boolean,
    getNextSteps: (Point) -> List<Point>
): List<List<Point>> {
    val allPaths = mutableListOf<List<Point>>()

    fun backtrack(current: Point, path: List<Point>) {
        val newPath = path + current

        if (isEndCondition(current)) {
            allPaths.add(newPath) // Salva una copia del percorso
        } else {
            for (next in getNextSteps(current)) {
                if (next !in newPath)
                    backtrack(next, newPath)
            }
        }

    }

    backtrack(startPoint, mutableListOf())

    return allPaths
}
