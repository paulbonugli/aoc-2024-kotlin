import kotlin.math.absoluteValue

fun main() {
    fun parseInput(input: List<String>) = input.map { line -> line.split(" ").map(String::toInt) }

    fun isSafe(input: List<Int>) : Boolean {
        val diffs = input
            .windowed(2)
            .map { (a, b) -> a - b }

        val allDecreasingOrIncreasing = diffs.all { it > 0 } or diffs.all { it < 0 }
        val differencesOK = diffs.map{ it.absoluteValue }.all { it <= 3 }

        return allDecreasingOrIncreasing and differencesOK
    }

    fun part1(input: List<String>) : Int {
        return parseInput(input).count(::isSafe)
    }

    fun part2(input: List<String>) : Int {
        return parseInput(input).count { nums ->
            isSafe(nums) or
                    nums.indices
                        .map { i -> nums.toMutableList().apply { removeAt(i) } }
                        .any { restrictedNums -> isSafe(restrictedNums) }
        }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
