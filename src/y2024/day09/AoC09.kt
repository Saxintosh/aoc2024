package y2024.day09

import Day

fun main() {
	AOC9
}


private object AOC9 : Day<Long, Long>(1928L, 2858L, 6279058075753L, 6301361958738) {

	@JvmInline
	value class Block(val id: Int?) {
		val isFile get() = id != null
		val isFree get() = id == null
	}

	sealed class Chunk(var pos: Int, var len: Int)
	class File(val id: Int, pos: Int, len: Int) : Chunk(pos, len)
	class Space(pos: Int, len: Int) : Chunk(pos, len)

	class Disk(val files: MutableList<File>, val spaces: MutableList<Space>) {

		fun move(f: File) {
			if (f.pos < spaces.first().pos)
				return

			val space = spaces.firstOrNull { s ->
				s.pos < f.pos && s.len >= f.len
			} ?: return

			// Add the space instead of the moved file.
			// At the moment is out of order but it is not important.
			// Put in order before the checksum!
			spaces.add(Space(f.pos, f.len))

			f.pos = space.pos

			if (f.len == space.len)
				spaces.remove(space)
			else {
				space.pos += f.len
				space.len -= f.len
			}

		}

		fun compact() {
			files.reversed().forEach { move(it) }
		}

		fun toBlocks(): ArrayList<Block> {
			val blocks = ArrayList<Block>()
			files.plus(spaces)
				.sortedBy { it.pos }
				.forEach { x ->
					when (x) {
						is File -> repeat(x.len) { blocks.add(Block(x.id)) }
						is Space -> repeat(x.len) { blocks.add(Block(null)) }
					}
				}
			return blocks
		}

		fun checksum(): Long {
			spaces.sortBy { it.pos }
            return toBlocks().checksum()
        }
	}

	fun String.process(): ArrayList<Block> {
		var isFile = true
		var index = 0
		val blocks = arrayListOf<Block>()
		for (ch in this) {
			if (isFile) {
				repeat(ch.digitToInt()) {
					blocks.add(Block(index))
				}
				index++
			} else {
				repeat(ch.digitToInt()) {
					blocks.add(Block(null))
				}
			}
			isFile = isFile.not()
		}
		return blocks
	}

	fun String.process2(): Disk {
		val files = mutableListOf<File>()
		var fileId = 0
		val spaces = mutableListOf<Space>()
		var isFile = true
		var pos = 0
		for (ch in this) {
			val len = ch.digitToInt()
			if (isFile)
				files.add(File(fileId++, pos, len))
			else
				spaces.add(Space(pos, len))
			pos += len
			isFile = isFile.not()
		}
		return Disk(files, spaces)
	}

	fun ArrayList<Block>.compact() {
		var currentIndices = indices
		while (currentIndices.last - currentIndices.first > 0) {
			if (this[currentIndices.last].isFree) {
				currentIndices = currentIndices.first..currentIndices.last - 1
				continue
			}
			if (this[currentIndices.first].isFile) {
				currentIndices = currentIndices.first + 1..currentIndices.last
				continue
			}
			this[currentIndices.first] = this[currentIndices.last]
			this[currentIndices.last] = Block(null)
			currentIndices = currentIndices.first + 1..currentIndices.last - 1
		}
	}

	fun ArrayList<Block>.checksum(): Long = asSequence()
		.withIndex()
		.sumOf {
			it.index.toLong() * (it.value.id ?: 0)
		}

	fun ArrayList<Block>.debug() = buildString {
		this@debug.forEach {
			if (it.id != null)
				append(it.id)
			else
				append(".")
		}
	}

	init {

		benchmarkRepetition = 10

		part1Text { txt ->
			val blocks = txt.process()
			blocks.compact()
			blocks.checksum()
		}


		part2Text { txt ->
			val disk = txt.process2()
			disk.compact()
			disk.checksum()
		}
	}
}