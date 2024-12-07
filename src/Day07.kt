import BridgeEquation.Companion.parseBridgeEquations

fun <T> cartesianProduct(list: List<T>, n: Int): List<List<T>> {
    if (n == 0) return listOf(emptyList())
    return cartesianProduct(list, n - 1).flatMap { prefix ->
        list.map { prefix + it }
    }
}

class BridgeEquation(input: String) {
    val testValue: Long
    private val numbers : List<Long>

    init {
        val splits = input.split(":")
        testValue = splits[0].toLong()
        numbers = splits[1].trim().split(" ").map { it.toLong() }
    }

    fun couldBeValid(operators: List<(Long, Long) -> Long> = listOf(Long::times, Long::plus)) : Boolean {
        val numOperators = numbers.size - 1
        return cartesianProduct(operators, numOperators)
            .map { operatorCombo -> ArrayDeque(operatorCombo) }
            .any { operatorCombo -> numbers.reduce { acc, num -> operatorCombo.removeFirst()(acc, num) } == testValue }
    }

    companion object {
        fun parseBridgeEquations(input: List<String>) : List<BridgeEquation> {
            return input.map(::BridgeEquation)
        }
    }
}

fun main() {
    fun part1(input: List<String>) : Long {
        return parseBridgeEquations(input)
            .filter(BridgeEquation::couldBeValid)
            .map(BridgeEquation::testValue)
            .sum()
    }

    fun concatenateOperator(first: Long, second: Long) : Long {
        return (first.toString() + second.toString()).toLong()
    }

    fun part2(input: List<String>) : Long {
        return parseBridgeEquations(input)
            .filter{ equation -> equation.couldBeValid(listOf(Long::times, Long::plus, ::concatenateOperator)) }
            .map(BridgeEquation::testValue)
            .sum()
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
