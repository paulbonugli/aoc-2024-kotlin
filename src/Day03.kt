import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>) : Int {
        return Regex("mul\\((\\d+),(\\d+)\\)")
            .findAll(input.joinToString())
            .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }

    fun part2(input: List<String>) : Int {
        return input.joinToString()
            // split into fragments to be done
            .split("do()")
            // we can ignore everything in the fragment following a don't()
            .map { fragment -> listOf(fragment.split("don't()")[0]) }
            // what's left we can process and then sum
            .sumOf(::part1)
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
