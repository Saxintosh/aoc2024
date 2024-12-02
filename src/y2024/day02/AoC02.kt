package y2024.day02

import Day
import kotlin.math.abs


fun main() {
	AOC2
}

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
//			if (!hasNext()) throw NoSuchElementException()
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
			a != b && (a > b == firstTest) && abs(a - b) <= 3
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
		part1Lines { lines ->
			lines
				.map { it.split(" ").map(String::toInt) }
				.count { it.isSafe() }
		}


		part2Lines { lines ->
			lines
				.map { it.split(" ").map(String::toInt) }
				.count { it.isSafeIsh() }
		}

		// With the wrapping class, it is slower!!!
		part2Lines { lines ->
			lines
				.map { it.split(" ").map(String::toInt) }
				.count { it.isSafeIsh2() }
		}
	}
}