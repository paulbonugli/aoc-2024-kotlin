import kotlin.math.absoluteValue

fun main() {
    // separate columns into two lists
    fun listPair(input: List<String>): Pair<List<Int>, List<Int>> {
        val lists = input
            .map { it.split("\\s+".toRegex()) }
            .map { it[0].toInt() to it[1].toInt() }
            .unzip()
        return lists
    }

    fun part1(input: List<String>) : Int {
        val lists = listPair(input)

        // sort each list independently and zip back together
        return lists.first.sorted()
            .zip(lists.second.sorted())
            .sumOf { (first, second) -> (first - second).absoluteValue }
    }

    fun part2(input: List<String>) : Int {
        val lists = listPair(input)

        val secondGrouped = lists.second
            .groupBy { it }
            .mapValues { it.value.size }

        return lists.first.sumOf {
            it * (secondGrouped[it] ?: 0)
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
