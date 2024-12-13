package y2024.day13

import Day

fun main() {
	AOC13
}

private object AOC13 : Day<Long, Long>(480, 1, 37901, 77407675412647) {

	data class Point(val x: Long, val y: Long)
	data class Machine(val a: Point, val b: Point, val prize: Point) {
		val det = a.x * b.y - a.y * b.x
		val detX = prize.x * b.y - prize.y * b.x
		val detY = a.x * prize.y - a.y * prize.x
		val hasSolutions = det != 0L
		val hasIntegerSolutions = det != 0L && detX % det == 0L && detY % det == 0L

		// Soluzioni
		val aNum = detX / det
		val bNum = detY / det
		val cost = aNum * 3L + bNum
	}

	fun parse(lines: List<String>, k: Long) = lines
		.filter { it.isNotBlank() }
		.windowed(3, 3).map {
			it.map { it.split("+", ",", "=").takeLast(3) }
		}
		.map {
			Machine(
				Point(it[0][0].toLong(), it[0][2].toLong()),
				Point(it[1][0].toLong(), it[1][2].toLong()),
				Point(it[2][0].toLong() + k, it[2][2].toLong() + k),
			)
		}

	init {

		part1Lines { lines ->
			parse(lines, 0L)
				.filter { it.hasIntegerSolutions }
				.sumOf { it.cost }

		}


		part2Lines(skipTest = true) { lines ->
			parse(lines, 10000000000000L)
				.filter { it.hasIntegerSolutions }
				.sumOf { it.cost }
		}
	}
}