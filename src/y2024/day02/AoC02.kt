package y2024.day02

import Day
import kotlin.math.absoluteValue


fun main() {
	AOC2
}

// I thought that creating a wrapper that reused the source list would be faster. But no.
// It is becoming more and more evident that creating many objects is not an expensive operation at all.
class ExcludingIndexList(private val list: List<Int>, var excludedIndex: Int = 0): List<Int> {
	init {
		require(excludedIndex in list.indices) { "Index $excludedIndex is out of bounds for list of size ${list.size}" }
	}

	override val size = list.size - 1

	private fun adjustedIndex(index: Int) = if (index >= excludedIndex) index + 1 else index
	override fun get(index: Int) = list[adjustedIndex(index)]
	override fun isEmpty() = list.size == 1

	override fun iterator(): Iterator<Int> = object: Iterator<Int> {
		private var currentIndex = 0

		override fun hasNext(): Boolean {
			return currentIndex < size
		}

		override fun next(): Int {
			if (!hasNext()) throw NoSuchElementException()
			return list[adjustedIndex(currentIndex++)]
		}
	}

	fun excluding(anIndex: Int) = this.also { excludedIndex = anIndex }

	override fun listIterator(): ListIterator<Int> = TODO("Not yet implemented")
	override fun listIterator(index: Int): ListIterator<Int> = TODO("Not yet implemented")
	override fun subList(fromIndex: Int, toIndex: Int): List<Int> = TODO("Not yet implemented")
	override fun lastIndexOf(element: Int): Int = TODO("Not yet implemented")
	override fun indexOf(element: Int): Int = TODO("Not yet implemented")
	override fun containsAll(elements: Collection<Int>): Boolean = TODO("Not yet implemented")
	override fun contains(element: Int): Boolean = TODO("Not yet implemented")
}

private object AOC2: Day<Int, Int>(2, 4, 549, 589, true) {

	private fun List<Int>.isSafe(): Boolean {
		val firstTest = get(0) > get(1)
		return windowed(2, 1).all { (a, b) ->
			a != b && (a > b == firstTest) && (a - b).absoluteValue <= 3
		}
	}

	// it seems that zipWithNext is a little faster than windowed
	private fun List<Int>.isSafeZip(): Boolean {
		val firstTest = get(0) > get(1)
		return zipWithNext().all{ (a, b) ->
			a != b && (a > b == firstTest) && (a - b).absoluteValue <= 3
		}
	}

	private fun List<Int>.isSafeIsh(): Boolean {
		if (isSafe()) return true

		return indices.any { pos -> this.filterIndexed { index, _ -> index != pos }.isSafe() }
	}

	private fun List<Int>.isSafeIsh2(): Boolean {
		if (isSafe()) return true

		val wrapper = ExcludingIndexList(this)
		return indices.any { wrapper.excluding(it).isSafe() }
	}

	init {
		part1Lines("isSafe") { lines ->
			lines
				.map { it.split(" ").map(String::toInt) }
				.count { it.isSafe() }
		}

		part1Lines("isSafeZip") { lines ->
			lines
				.map { it.split(" ").map(String::toInt) }
				.count { it.isSafeZip() }
		}

		part2Lines("filterIndexed") { lines ->
			lines
				.map { it.split(" ").map(String::toInt) }
				.count { it.isSafeIsh() }
		}

		part2Lines("with Wrapper") { lines ->
			lines
				.map { it.split(" ").map(String::toInt) }
				.count { it.isSafeIsh2() }
		}
	}
}