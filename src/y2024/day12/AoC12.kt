package y2024.day12

import Day
import y2024.ChGrid
import y2024.Point

fun main() {
	AOC12
}

private object AOC12: Day<Long, Long>(140, 80, 1451030, 859494) {

	data class Segment(var start: Point, var end: Point)

	fun mergeSegments(segments: List<Segment>): List<Segment> {
		if (segments.isEmpty()) return emptyList()

		val mergedSegments = mutableListOf<Segment>(segments[0])

		for (nextSegment in segments.drop(1)) {
			var s = mergedSegments.firstOrNull { it.end == nextSegment.start }
			if (s != null) {
				s.end = nextSegment.end
			} else {
				s = mergedSegments.firstOrNull { it.start == nextSegment.end }
				if (s != null) {
					s.start = nextSegment.start
				} else {
					mergedSegments.add(nextSegment)
				}
			}
		}

		return mergedSegments
	}

	class Region(val ch: Char, val set: MutableSet<Point>) {
		fun area() = set.size
		fun perimeter() = set.sumOf { p -> p.crossAdjacent().count { it !in set } }

		fun perimeter2(): Int {
			val h1SidesU = mutableListOf<Segment>()
			val h1SidesD = mutableListOf<Segment>()
			val v1SidesL = mutableListOf<Segment>()
			val v1SidesR = mutableListOf<Segment>()
			set.forEach { p ->
				if (p.up() !in set) {
					h1SidesU.add(Segment(p, p.right()))
				}
				if (p.down() !in set) {
					h1SidesD.add(Segment(p.down(), p.down().right()))
				}
				if (p.left() !in set) {
					v1SidesL.add(Segment(p, p.up()))
				}
				if (p.right() !in set) {
					v1SidesR.add(Segment(p.right(), p.right().up()))
				}
			}


			return mergeSegments(h1SidesU).size + mergeSegments(h1SidesD).size + mergeSegments(v1SidesL).size + mergeSegments(v1SidesR).size
		}
	}

	fun process(g: ChGrid): MutableSet<Region> {

		val regions = mutableSetOf<Region>()
		fun findRegion(p: Point, ch: Char): Region = regions.first { ch == it.ch && p in it.set }
		fun findMyRegion(p: Point, ch: Char): Region? = regions.firstOrNull { ch == it.ch && p in it.set }

		g.asPointsSequenceAndValue().forEach { (p, ch) ->
			val l = g.extract(listOf(p.up(), p.left()))
				.filter { it.second == ch }
			when (l.size) {
				2    -> {
					// 2 region to join with me (if not the same)
					val r1 = findRegion(l[0].first, ch)
					val r2 = findRegion(l[1].first, ch)
					if (r1 != r2) {
						r1.set.addAll(r2.set)
						regions.remove(r2)
					}
					r1.set.add(p)
				}

				1    -> {
					// found 1 region
					val r1 = findRegion(l[0].first, ch)
					r1.set.add(p)
				}

				else -> {
					// First region with Ch
					regions.add(Region(ch, mutableSetOf(p)))
				}
			}
		}

		return regions
	}

	init {

		val t = """
			AAAAAA
			AAABBA
			AAABBA
			ABBAAA
			ABBAAA
			AAAAAA
		""".trimIndent()

		val g = ChGrid(t.lines())
		val r = process(g)
		val s = r.sumOf {
			println("${it.ch}: ${it.area()} * ${it.perimeter2()}")
			it.area() * it.perimeter2().toLong()
		}
		println("s: $s")

		part1Lines { lines ->
			val grid = ChGrid(lines)
			val regions = process(grid)
			regions.sumOf {
				it.area() * it.perimeter().toLong()
			}
		}


		part2Lines { lines ->
			val grid = ChGrid(lines)
			val regions = process(grid)
			regions.sumOf {
				it.area() * it.perimeter2().toLong()
			}
		}
	}
}