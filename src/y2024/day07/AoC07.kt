package y2024.day07

import Day
import y2024.*


fun main() {
    AOC7
}

private object AOC7 : Day<Long, Long>(3749L, 11387L, 1153997401072L, 97902809384118L) {

    fun concat(l1:Long, l2:Long): Long = (l1.toString() + l2.toString()).toLong()
    val ops = listOf<Long.(Long) -> Long>(Long::plus, Long::times)
    val ops2 = listOf<Long.(Long) -> Long>(Long::plus, Long::times, ::concat)

    data class Test(val res: Long, val values: List<Long>) {

        private fun process(o: List<Long.(Long) -> Long>) = values.drop(1).foldIndexed(values[0]) { i, acc, e ->
            o[i](acc, e)
        }

        fun isPossible(): Boolean {
            val operations: Sequence<List<Long.(Long) -> Long>> = ops.cartesianProduct(values.size - 1)
            return operations.any { process(it) == res }
        }

        fun isPossible2(): Boolean {
            val operations: Sequence<List<Long.(Long) -> Long>> = ops2.cartesianProduct(values.size - 1)
            return operations.any { process(it) == res }
        }

        companion object {
            fun parse(s: String): Test {
                val (r, l) = s.split(": ")
                val ls = l.split(" ").map(String::toLong)
                return Test(r.toLong(), ls)
            }
        }
    }

    init {
        part1Lines { lines ->
            lines
                .map { Test.parse(it) }
                .filter { it.isPossible() }
                .sumOf { it.res }
        }

        part2Lines { lines ->
            lines
                .map { Test.parse(it) }
                .filter { it.isPossible2() }
                .sumOf { it.res }
        }
    }
}