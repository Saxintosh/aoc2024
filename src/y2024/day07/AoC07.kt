package y2024.day07

import Day
import kotlin.math.pow

fun main() {
    AOC7
}

/**
 * converts an index of the possible cartesian products
 * to the list itself.
 *
 * The idea is to treat the combination as a number in a “base” system where each element is a “digit”.
 * We treat the index as a number and break it down using division and modulus to select the corresponding elements from the list.
 */
private fun <T> indexToCombination(index: Int, elements: List<T>, n: Int): List<T> {
    val indices = IntArray(n)
    var tempIndex = index

    for (i in 0 until n) {
        val elementIndex = tempIndex % elements.size
        indices[n - 1 - i] = elementIndex
        tempIndex /= elements.size
    }

    return indices.map { elements[it] }
}

private fun <T> List<T>.cartesianProduct(n: Int): Sequence<List<T>> = sequence {
    val count = size.toDouble().pow(n).toInt()
    for (i in 0 until count) {
        yield(indexToCombination(i, this@cartesianProduct, n))
    }
}

// or...


private object AOC7 : Day<Long, Long>(3749L, 11387L, 1153997401072L, 97902809384118L) {

    fun concat(l1: Long, l2: Long): Long = (l1.toString() + l2.toString()).toLong()
    val ops = listOf<Long.(Long) -> Long>(Long::plus, Long::times)
    val opsExtended = listOf<Long.(Long) -> Long>(Long::plus, Long::times, ::concat)

    data class Test(val res: Long, val values: List<Long>) {

        private fun process(o: List<Long.(Long) -> Long>) = values.drop(1).foldIndexed(values[0]) { i, acc, e ->
            o[i](acc, e)
        }

        fun isPossible(opList: List<Long.(Long) -> Long>): Boolean {
            val operations: Sequence<List<Long.(Long) -> Long>> = opList.cartesianProduct(values.size - 1)
            return operations.any { process(it) == res }
        }

        private fun isPossibleRec(p1: Long, ll: List<Long>, oo: List<Long.(Long) -> Long>): Boolean {
            if (p1 > res)
                return false

            if (ll.isEmpty())
                return res == p1

            return oo.any {
                val r = it(p1, ll[0])
                isPossibleRec(r, ll.drop(1), oo)
            }
        }

        fun isPossibleRec(oo: List<Long.(Long) -> Long>) = isPossibleRec(values.first(), values.drop(1), oo)


        companion object {
            fun parse(s: String): Test {
                val (r, l) = s.split(": ")
                val ls = l.split(" ").map(String::toLong)
                return Test(r.toLong(), ls)
            }
        }
    }

    init {

        part1Lines("indices") { lines ->
            lines
                .map { Test.parse(it) }
                .filter { it.isPossible(ops) }
                .sumOf { it.res }
        }

        part1Lines("recursive") { lines ->
            lines
                .map { Test.parse(it) }
                .filter { it.isPossibleRec(ops) }
                .sumOf { it.res }
        }

//        part2Lines { lines ->
//            lines
//                .map { Test.parse(it) }
//                .filter { it.isPossible(opsExtended) }
//                .sumOf { it.res }
//        }

        part2Lines("recursive") { lines ->
            lines
                .map { Test.parse(it) }
                .filter { it.isPossibleRec(opsExtended) }
                .sumOf { it.res }
        }

    }
}