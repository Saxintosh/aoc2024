package y2024.day05

import Day
import y2024.getOrderedPairs


fun main() {
    AOC5
}

private object AOC5 : Day<Int, Int>(143, 123, 4185, 2048) {

    fun parse(txt: String): Pair<Set<Pair<String, String>>, List<List<String>>> {
        val (p1, p2) = txt.split("\n\n")
        val rules = p1.lines().map {
            val (x, y) = it.split("|")
            x to y
        }.toSet()
        val updates = p2.lines().map { it.split(",") }
        return rules to updates
    }

    fun List<String>.isOrdered(rules: Set<Pair<String, String>>): Boolean {
        val pairs = this.getOrderedPairs()
        return pairs.all { it in rules }
    }

    init {
        benchmarkRepetition = 100

        part1Text { txt ->
            val (rules, updates) = parse(txt)
            updates
                .filter { it.isOrdered(rules) }
                .sumOf {
                    it[it.size / 2].toInt()
                }
        }


        part2Text { txt ->
            val (rules, updates) = parse(txt)
            updates
                .filter { !it.isOrdered(rules) }
                .map {
                    it.sortedWith { o1, o2 -> if (o1 to o2 in rules) 1 else -1 }
                }.sumOf {
                    it[it.size / 2].toInt()
                }
        }
    }
}