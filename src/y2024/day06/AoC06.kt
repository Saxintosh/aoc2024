package y2024.day06

import Day
import y2024.ChGrid
import y2024.Direction
import y2024.Point


fun main() {
    AOC6
}

private object AOC6 : Day<Int, Int>(41, 6, 5453, 2188) {

    fun ChGrid.move(p: Point, dir: Direction): Pair<Point, Direction> {
        val nextP = p.go(dir)
        val nextCh = this[nextP]
        return if (nextCh == '#')
            p to dir.turnRight()
        else
            nextP to dir
    }

    fun ChGrid.patrol(firstP: Point, firstDir: Direction, set: MutableSet<Point>) {
        var p = firstP
        var dir = firstDir
        set.add(p)
        while (true) {
            this.move(p, dir).let {
                p = it.first
                dir = it.second
                if (this[p] == null) return
                set.add(p)
            }
        }
    }

    fun ChGrid.isLoop(firstP: Point, firstDir: Direction): Boolean {
        val set = mutableSetOf(firstP to firstDir)
        var p = firstP
        var dir = firstDir
        while (true) {
            val state = this.move(p, dir)
            val newP = state.first
            dir = state.second
            if (this[newP] == null) return false
            if (state in set) return true
            p = newP
            set.add(state)
        }
    }

    init {
        part1Lines { lines ->
            val grid = ChGrid(lines)
            val originalGuardP = grid.asPointsSequence().first { grid[it] == '^' }

            val visited = mutableSetOf(originalGuardP)
            grid.patrol(originalGuardP, Direction.Up, visited)

            visited.size
        }

        part2Lines { lines ->
            val grid = ChGrid(lines)
            val originalGuardP = grid.asPointsSequence().first { grid[it] == '^' }

            val visited = mutableSetOf(originalGuardP)
            grid.patrol(originalGuardP, Direction.Up, visited)

            visited.remove(originalGuardP)

            visited.count { newHash ->
                grid[newHash] = '#'
                grid.isLoop(originalGuardP, Direction.Up).also { grid[newHash] = '.' }
            }
        }
    }
}