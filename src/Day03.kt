import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>) : Int {
        return Regex("mul\\((\\d+),(\\d+)\\)")
            .findAll(input.joinToString())
            .map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
            .sum()
    }

    fun part2(input: List<String>) : Int {
        return 0
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
