import java.io.File
import java.io.FileNotFoundException
import kotlin.system.exitProcess
import kotlin.time.measureTimedValue

@DslMarker
annotation class AdventOfCode

@AdventOfCode
open class Day<T1, T2>(
	testRes1: T1,
	testRes2: T2,
	realRes1: T1? = null,
	realRes2: T2? = null,
) {
	var benchmarkRepetition: Int = 1
	var isTest = false
	private set
	private val srcPath: String

	private val openRed = "\u001B[31m"
	private val openGreen = "\u001B[32m"
	private val openYellow = "\u001B[33m"
	private val normalColor = "\u001B[0m"

	init {
		val e = Exception()
		val s = e.stackTrace.first { it.className.startsWith("y") }
		val path = s.className.split(".").dropLast(1).joinToString("/")
		srcPath = "src/$path/"
	}

	private val fInput = File(srcPath + "input.txt")
		.takeIf { it.exists() } ?: throw FileNotFoundException()
	private val fTest1 = File(srcPath + "test.txt")
		.takeIf { it.exists() } ?: throw FileNotFoundException()
	private var fTest2 = File(srcPath + "test2.txt")
		.takeIf { it.exists() } ?: File(srcPath + "test.txt")
		.takeIf { it.exists() } ?: throw FileNotFoundException()

	private val fTests = listOf(fTest1, fTest2)
	private val testResults = listOf(testRes1, testRes2)
	private val properResults = listOf(realRes1, realRes2)

	private fun <SRC, T> part(label: String, part: Int, skipTest: Boolean, reader: File.() -> SRC, block: (SRC) -> T) {
		println("[$label]:")
		if (!skipTest) {
			val lines = fTests[part].reader()
			isTest = true
			val res = measureTimedValue { block(lines) }
			println("${openGreen}Test Part ${part + 1}$normalColor = ${res.value}")
			if (res.value != testResults[part]) {
				println("$openRed              ERROR:$normalColor ${testResults[part]} expected!")
				exitProcess(1)
			}
		}
		val lines2 = fInput.reader()
		isTest = false
		var res2 = measureTimedValue { block(lines2) }
		var extraInfo = ""
		if (benchmarkRepetition > 1) {
			extraInfo = " out of $benchmarkRepetition"
			res2 = measureTimedValue {
				repeat(benchmarkRepetition - 1) { block(lines2) }
				block(lines2)
			}
		}

		println("$openGreen     Part ${part + 1}$normalColor = ${res2.value} in ${res2.duration.div(benchmarkRepetition)}$extraInfo")
		properResults[part]?.let {
			if (it != res2.value) {
				println("$openRed              ERROR:$normalColor $it expected!")
				exitProcess(1)
			}
		}
	}

	@AdventOfCode
	fun part1Lines(label: String = "", skipTest: Boolean = false, block: (List<String>) -> T1) =
		part(label, 0, skipTest, File::readLines, block)

	@AdventOfCode
	fun part2Lines(label: String = "", skipTest: Boolean = false, block: (List<String>) -> T2) =
		part(label, 1, skipTest, File::readLines, block)

	@AdventOfCode
	fun part1Text(label: String = "", skipTest: Boolean = false, block: (String) -> T1) =
		part(label, 0, skipTest, File::readText, block)

	@AdventOfCode
	fun part2Text(label: String = "", skipTest: Boolean = false, block: (String) -> T2) =
		part(label, 1, skipTest, File::readText, block)

}
