package y2024.day01

import Day
import kotlin.math.abs


fun main() {
	AOC1
}

private object AOC1: Day<Int, Int>(11, 31, 1938424, 22014209) {

	init {

		part1Lines { lines ->
			val pairs = lines.map { it.split("   ").map(String::toInt) }
			val l1 = pairs.map { it[0] }.sorted()
			val l2 = pairs.map { it[1] }.sorted()
			val zip = l1.zip(l2)

			zip.sumOf { abs(it.first - it.second) }
		}

		part1Lines { lines ->
			lines
				.map {
					val nums = it.split("   ").map(String::toInt)
					nums[0] to nums[1]
				}
				.unzip()
				.let { it.first.sorted() zip it.second.sorted() }
				.sumOf { abs(it.first - it.second) }
		}

		part2Lines { lines ->
			val pairs = lines.map { it.split("   ").map(String::toInt) }
			val l1 = pairs.map { it[0] }.sorted()
			val l2 = pairs.map { it[1] }.sorted()
			l1.sumOf { first -> first * l2.count { it == first } }
		}

		part2Lines { lines ->
			val pairs = lines.map { it.split("   ").map(String::toInt) }
			val l1 = pairs.map { it[0] }.sorted()
			val l2 = pairs.map { it[1] }.sorted()
			val l2Group = l2.groupingBy { it }.eachCount()
			l1.sumOf { it * l2Group.getOrDefault(it, 0) }
		}
	}
}