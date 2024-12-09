package y2024.day08

import Day
import y2024.ChGrid
import y2024.Point
import y2024.Vector
import y2024.allDistinctPairs

fun main() {
    AOC8
}

private object AOC8 : Day<Int, Int>(14, 34, 364, 1231) {

    private fun findAntinodes1(grid: ChGrid, pair: Pair<Point, Point>): List<Point> {
        val (p1, p2) = pair
        val v = Vector.from(p1, p2)
        return listOf(p1 - v, p2 + v).filter { it in grid }
    }

    private fun findAntinodes2(grid: ChGrid, pair: Pair<Point, Point>): List<Point> {
        var (p1, p2) = pair
        val v = Vector.from(p1, p2)
        val antinodes = arrayListOf(p1, p2)

        while (p1 in grid) {
            antinodes.add(p1)
            p1 -= v
        }

        while (p2 in grid) {
            antinodes.add(p2)
            p2 += v
        }

        return antinodes
    }

    init {

        benchmarkRepetition = 100

        part1Lines { lines ->
            val grid = ChGrid(lines)
            grid.asPointsSequenceAndValue()
                .filter { it.second != '.' }
                .groupBy(keySelector = { it.second!! }, valueTransform = { it.first })
                .flatMap { (_, l) ->
                    l.allDistinctPairs().flatMap { pair ->
                        findAntinodes1(grid, pair)
                    }
                }
                .toSet()
                .count()
        }


        part2Lines { lines ->
            val grid = ChGrid(lines)
            grid.asPointsSequenceAndValue()
                .filter { it.second != '.' }
                .groupBy(keySelector = { it.second!! }, valueTransform = { it.first })
                .flatMap { (_, l) ->
                    l.allDistinctPairs().flatMap { pair ->
                        findAntinodes2(grid, pair)
                    }
                }
                .toSet()
                .count()
        }

    }
}