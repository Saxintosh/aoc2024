package y2024

import java.lang.Math.pow
import kotlin.math.abs
import kotlin.math.pow

fun myRange(a: Int, b: Int) = when (a < b) {
	true -> a..b
	else -> b..a
}

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

fun <P> findPathsFrom(
    startPoint: P,
    isEndCondition: (P) -> Boolean,
    getNextSteps: (P) -> List<P>
): List<List<P>> {
    val allPaths = mutableListOf<List<P>>()

    fun backtrack(current: P, path: List<P>) {
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


tailrec fun gcd(a: Long, b: Long): Long {
	return if (b == 0L) a else gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
	return if (a == 0L || b == 0L) 0L else abs(a * b) / gcd(a, b)
}

fun findLCM(numbers: List<Long>): Long {
	if (numbers.isEmpty()) {
		throw IllegalArgumentException("List cannot be empty")
	}

	var result = numbers[0]

	for (i in 1 until numbers.size) {
		result = lcm(result, numbers[i])
	}

	return result
}