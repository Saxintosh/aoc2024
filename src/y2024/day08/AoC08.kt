package y2024.day08

import Day
import y2024.ChGrid
import y2024.Point
import y2024.Vector
import y2024.allDistinctPairs
import kotlin.math.pow

fun main() {
    AOC8
}

private object AOC8 : Day<Int, Int>(14, 34, 364, 1231) {

    private fun findAntinodes1(grid: ChGrid, pair: Pair<Point, Point>): List<Point> {
        val (p1, p2) = pair
        val v = Vector.from(p1, p2)
        return listOf<Point>(p1 - v, p2 + v).filter { it in grid }
    }

    private fun findAntinodes2(grid: ChGrid, pair: Pair<Point, Point>): List<Point> {
        var (p1, p2) = pair
        val v = Vector.from(p1, p2)
        val antinodes = mutableListOf<Point>(p1, p2)

        while (p1 in grid) {
            p1 -= v
            if (p1 in grid)
                antinodes.add(p1)
        }

        while (p2 in grid) {
            p2 += v
            if (p2 in grid)
                antinodes.add(p2)
        }

        return antinodes
    }

    init {

        benchmarkRepetition = 100
        
        part1Lines { lines ->
            val grid = ChGrid(lines)
            val antennas = grid.asPointsSequenceAndValue()
                .filter { it.second != '.' }
                .groupBy(keySelector = { it.second!! }, valueTransform = { it.first })

            antennas.flatMap { (_, l) ->
                l.allDistinctPairs().flatMap { pair ->
                    findAntinodes1(grid, pair)
                }
            }
                .toSet()
                .count()
        }


        part2Lines { lines ->
            val grid = ChGrid(lines)
            val antennas = grid.asPointsSequenceAndValue()
                .filter { it.second != '.' }
                .groupBy(keySelector = { it.second!! }, valueTransform = { it.first })

            antennas.flatMap { (_, l) ->
                l.allDistinctPairs().flatMap { pair ->
                    findAntinodes2(grid, pair)
                }
            }
                .toSet()
                .count()
        }

    }
}