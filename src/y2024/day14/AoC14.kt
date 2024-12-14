package y2024.day14

import Day
import y2024.Point
import y2024.myRange

fun main() {
    AOC14
}

private object AOC14 : Day<Long, Long>(12, 0, 220971520, 6355) {

    data class Velocity(val onX: Int, val onY: Int)

    data class Robot(val start: Point, val vel: Velocity) {
        var pos = start
    }

    fun parse(line: String): Robot {
        val a = line.split("p=", ",", " v=").drop(1).map(String::toInt)
        return Robot(Point(a[0], a[1]), Velocity(a[2], a[3]))
    }

    var hSize = 0
    var vSize = 0

    fun Robot.move() {
        var (x, y) = pos
        x = (x + vel.onX) % hSize
        y = (y + vel.onY) % vSize
        if (x < 0) x += hSize
        if (y < 0) y += vSize

        pos = Point(x, y)
    }

    data class Area(val p1: Point, val p2: Point) {
        val xRange = myRange(p1.x, p2.x)
        val yRange = myRange(p1.y, p2.y)
    }

    operator fun Area.contains(p: Point) = p.x in xRange && p.y in yRange


    init {

        part1Lines { lines ->
            if (isTest) {
                hSize = 11
                vSize = 7
            } else {
                hSize = 101
                vSize = 103
            }

            val l = lines.map(::parse)
            l.forEach { r ->
                repeat(100) { r.move() }
            }

            val center = Point((hSize - 1) / 2, (vSize - 1) / 2)
            val q1 = Area(center.upLeft(), Point(0, 0))
            val q2 = Area(center.upRight(), Point(hSize - 1, 0))
            val q3 = Area(center.downRight(), Point(hSize - 1, vSize - 1))
            val q4 = Area(center.downLeft(), Point(0, vSize - 1))
            val set1 = mutableSetOf<Robot>()
            val set2 = mutableSetOf<Robot>()
            val set3 = mutableSetOf<Robot>()
            val set4 = mutableSetOf<Robot>()
            l.forEach {
                when (it.pos) {
                    in q1 -> set1.add(it)
                    in q2 -> set2.add(it)
                    in q3 -> set3.add(it)
                    in q4 -> set4.add(it)
                }
            }

            set1.size.toLong() * set2.size * set3.size * set4.size
        }

        fun Array<Char>.lineOfMoreThan(count: Int): Boolean {
            var currentCount = 0
            for (ch in this) {
                if (ch == '#') {
                    currentCount++
                    if (currentCount >= count) {
                        return true
                    }
                } else {
                    currentCount = 0
                }
            }
            return false
        }

        fun draw(list: List<Robot>) = Array(vSize) {
            Array(hSize) { ' ' }
        }.apply { list.forEach { this[it.pos.y][it.pos.x] = '#' } }

        part2Lines(skipTest = true) { lines ->
            if (isTest) {
                hSize = 11
                vSize = 7
            } else {
                hSize = 101
                vSize = 103
            }

            val l = lines.map(::parse)
            repeat(10000) { cycle ->
                l.forEach { it.move() }
                val canvas = draw(l)
                (0..<vSize).any { lineIndex ->
                    if (canvas[lineIndex].lineOfMoreThan(10)) {
                        println("Cycle: $cycle")
                        canvas.forEach { println(it.joinToString(separator = "")) }
                        return@part2Lines cycle+1L
                    }
                    false
                }
            }

            0
        }
    }
}