package y2024.day10

import Day
import y2024.ChGrid
import y2024.findPathsFrom

fun main() {
	AOC10
}

private object AOC10 : Day<Int, Int>(36, 81, 582, 1302) {

	init {

		benchmarkRepetition = 10

		part1Lines { lines ->
			val g = ChGrid(lines)
			val startingPoints = g.asPointsSequenceAndValue().filter { it.second == '0' }.map { it.first }
			startingPoints
				.sumOf { start ->
					findPathsFrom(start, { g[it] == '9' }) { p ->
						val currVal = g[p]!!
						val nextVal = currVal + 1
						p.crossAdjacent().filter {
							g[it] == nextVal
						}
					}.distinctBy { it.last() }.count()
				}
		}


		part2Lines { lines ->
			val g = ChGrid(lines)
			val startingPoints = g.asPointsSequenceAndValue().filter { it.second == '0' }.map { it.first }
			startingPoints
				.sumOf { start ->
					findPathsFrom(start, { g[it] == '9' }) { p ->
						val currVal = g[p]!!
						val nextVal = currVal + 1
						p.crossAdjacent().filter {
							g[it] == nextVal
						}
					}.count()
				}
		}
	}
}