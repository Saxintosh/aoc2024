package y2024.day04

import Day
import y2024.ChGrid
import y2024.Point


fun main() {
	AOC4
}

private object AOC4 : Day<Int, Int>(18, 9, 2685, 2048) {

	init {
		benchmarkRepetition = 100

		part1Lines("optimized with mapNotNull? No!") { lines ->
			val grid = ChGrid(lines)
			val list = grid.asPointsSequence()
				.filter { grid[it] == 'X' }
				.map { p1 ->
					p1.adjacent().filter { grid[it] == 'M' }.map {
						it to Point.buildMove(p1, it)
					}
				}
				.flatMap { it }
				.mapNotNull {
					val move = it.second
					val nextp = it.first.move()
					if (grid[nextp] == 'A')
						nextp to move
					else null
				}
				.mapNotNull {
					val move = it.second
					val nextp = it.first.move()
					if (grid[nextp] == 'S')
						nextp to move
					else null
				}
				.toList()
			list.size
		}

		part1Lines("1 filters") { lines ->
			val grid = ChGrid(lines)
			val list = grid.asPointsSequence()
				.filter { grid[it] == 'X' }
				.map { p1 ->
					p1.adjacent().filter { grid[it] == 'M' }.map {
						it to Point.buildMove(p1, it)
					}
				}
				.flatMap { it }
				.filter {
					val move = it.second
					val nextp = it.first.move()
					grid[nextp] == 'A' && grid[nextp.move()] == 'S'

				}
				.toList()
			list.size
		}

		part1Lines("2 filters") { lines ->
			val grid = ChGrid(lines)
			val list = grid.asPointsSequence()
				.filter { grid[it] == 'X' }
				.map { p1 ->
					p1.adjacent().filter { grid[it] == 'M' }.map {
						it to Point.buildMove(p1, it)
					}
				}
				.flatMap { it }
				.filter {
					val move = it.second
					val nextp = it.first.move()
					grid[nextp] == 'A'

				}
				.filter {
					val move = it.second
					val nextp = it.first.move().move()
					grid[nextp] == 'S'
				}
				.toList()
			list.size
		}

		part2Lines { lines ->
			val grid = ChGrid(lines)
			fun checkXMas(p: Point): Boolean {
				val c1 = grid[p.upLeft()]
				val c2 = grid[p.upRight()]
				val c3 = grid[p.downRight()]
				val c4 = grid[p.downLeft()]
				val r1 = if (c1 == 'M' && c3 == 'S') 1 else 0
				val r2 = if (c2 == 'M' && c4 == 'S') 1 else 0
				val r3 = if (c3 == 'M' && c1 == 'S') 1 else 0
				val r4 = if (c4 == 'M' && c2 == 'S') 1 else 0
				return r1 + r2 + r3 + r4 == 2
			}

			val list = grid.asPointsSequence()
				.filter {
					grid[it] == 'A' && checkXMas(it)
				}
				.toList()
			list.size
		}
	}
}