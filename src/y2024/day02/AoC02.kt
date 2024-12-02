package y2024.day02

import Day
import kotlin.math.abs


fun main() {
	AOC2
}

private object AOC2: Day<Int, Int>(2, 4, 549, 22014209) {

	private fun List<Int>.isSafe(): Boolean {
		val firstTest = get(0) > get(1)
		return windowed(2, 1).all { (a, b) ->
			a != b && (a > b == firstTest) && abs(a - b) <= 3
		}
	}

	private fun List<Int>.isSafeIsh(): Boolean {
		if (isSafe()) return true

		return indices.any { pos -> this.filterIndexed { index, i -> index != pos }.isSafe() }
	}

	init {

		benchmark = false

		part1Lines { lines ->
			lines
				.map { it.split(" ").map(String::toInt) }
				.count { it.isSafe() }
		}


		part2Lines { lines ->
			lines
				.map { it.split(" ").map(String::toInt) }
				.count { it.isSafeIsh() }
		}
	}
}