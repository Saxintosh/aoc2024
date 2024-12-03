package y2024.day03

import Day


fun main() {
	AOC3
}


private object AOC3: Day<Int, Int>(161, 48, 178794710, 76729637, true) {

	val regex = Regex("""mul\((\d+),\s*(\d+)\)""")
	val regex2 = Regex("""mul\((\d+),\s*(\d+)\)|don't\(\)|do\(\)""")

	init {
		part1Text("isSafe") { txt ->
			regex
				.findAll(txt)
				.map {
					val x = it.groupValues[1].toInt()
					val y = it.groupValues[2].toInt()
					x * y
				}
				.sum()
		}


		part1Text("with Wrapper") { txt ->
			var guard = true
			regex2.findAll(txt).fold(0) { acc: Int, match: MatchResult ->
				when (match.value) {
					"don't()" -> acc.also { guard = false }
					"do()"    -> acc.also { guard = true }
					else      -> if (guard) acc + match.groupValues[1].toInt() * match.groupValues[2].toInt() else acc
				}
			}
		}
	}
}