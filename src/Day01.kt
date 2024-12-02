import kotlin.math.absoluteValue

fun main() {
    // separate columns into two lists
    fun listPair(input: List<String>): Pair<List<Int>, List<Int>> {
        return input
            .map { it.split("\\s+".toRegex()) }
            .map { it[0].toInt() to it[1].toInt() }
            .unzip()
    }

    fun part1(input: List<String>) : Int {
        val (list1, list2) = listPair(input)

        // sort each list independently and zip back together
        return list1.sorted()
            .zip(list2.sorted())
            .sumOf { (a, b) -> (a - b).absoluteValue }
    }

    fun part2(input: List<String>) : Int {
        val (list1, list2) = listPair(input)

        val list2frequencies = list2
            .groupBy { it }
            .mapValues { it.value.size }

        return list1.sumOf {
            it * (list2frequencies[it] ?: 0)
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
