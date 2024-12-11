package y2024.day11

import Day
import y2024.ChGrid
import y2024.Point

fun main() {
	AOC11
}

private object AOC11 : Day<Long, Long>(55312, 1, 198089, 236302670835517) {

	fun List<Long>.process(rep: Int): Long {

		val cache = HashMap<Pair<Long, Int>, Long>()

		fun solve(stone: Long, t: Int): Long {
			val p = stone to t
			if (p in cache) return cache[p]!!

			if (t == 0)
				return 1

			val thisStep = if (stone == 0L)
				solve(1, t - 1)
			else {
				val str = stone.toString()
				if (str.length % 2 == 0) {
					val l = str.length / 2
					val x = str.substring(0, l).toLong()
					val y = str.substring(l).toLong()
					solve(x, t - 1) + solve(y, t - 1)
				} else {
					solve(stone * 2024, t - 1)
				}
			}

			cache[stone to t] = thisStep
			return thisStep
		}

		return this.sumOf { solve(it, rep) }
	}

	init {

		part1Text { txt ->
			val l = txt.split(" ").map(String::toLong)
			l.process(25)
		}


		part2Text(skipTest = true) { txt ->
			val l = txt.split(" ").map(String::toLong)
			l.process(75)
		}
	}
}