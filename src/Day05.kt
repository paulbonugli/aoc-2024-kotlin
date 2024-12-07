import Rule.Companion.parseRules
import SafetyManualUpdate.Companion.parseUpdates
import SafetyManualUpdate.Companion.rearrange

class Rule(line: String) {
    private val first : Int
    private val second : Int

    init {
        line.split("|").map(String::toInt).let { first = it[0]; second = it[1] }
    }

    fun isAcceptable(update : SafetyManualUpdate) : Boolean {
        val firstIndex = update.indexOf(first)
        val secondIndex = update.indexOf(second)

        return firstIndex == null || secondIndex == null || firstIndex < secondIndex
    }

    fun isRelevant(update : SafetyManualUpdate) : Boolean {
        return update.indexOf(first) != null && update.indexOf(second) != null
    }

    companion object {
        fun parseRules(input: List<String>) : List<Rule>{
            return input.map(::Rule)
        }
    }
}

class SafetyManualUpdate {
    private val pages : List<Int>

    constructor(pages: List<Int>) {
        this.pages = pages.toList()
    }

    constructor(input: String) {
        this.pages = input.split(",").map(String::toInt)
    }

    fun indexOf(pageNum: Int) : Int? {
        return pages.indexOf(pageNum).takeIf { it >= 0 }
    }

    fun middlePage() : Int {
        return pages[pages.size / 2]
    }

    companion object {
        fun parseUpdates(input: List<String>) : List<SafetyManualUpdate> {
            return input.map(::SafetyManualUpdate)
        }

        fun rearrange(invalidUpdate: SafetyManualUpdate, rules: List<Rule>) : SafetyManualUpdate {
            val validOrder = mutableListOf<Int>()

            // insert each page in turn
            invalidUpdate.pages.forEach { page ->
                // find the first index in which we wouldn't invalidate the rules
                val chosenIndexForPage = IntRange(0, validOrder.size).first { i ->
                    val tempUpdate = SafetyManualUpdate(ArrayList(validOrder).apply { add(i, page) })
                    rules.all { rule -> rule.isAcceptable(tempUpdate) }
                }
                validOrder.add(chosenIndexForPage, page)
            }

            return SafetyManualUpdate(validOrder)
        }
    }
}


fun main() {
    fun part1(inputRules: List<String>, inputUpdates: List<String>) : Int {
        val rules = parseRules(inputRules)
        return parseUpdates(inputUpdates)
            .filter { update -> rules.all { rule -> rule.isAcceptable(update) } }
            .sumOf { update -> update.middlePage() }
    }

    fun part2(inputRules: List<String>, inputUpdates: List<String>) : Int {
        val rules = parseRules(inputRules)
        return parseUpdates(inputUpdates)
            .filter { update -> rules.any { rule -> !rule.isAcceptable(update) } }
            .map { update -> rearrange(update, rules.filter { it.isRelevant(update) }) }
            .sumOf { update -> update.middlePage() }
    }

    val inputRules = readInput("Day05rules")
    val inputUpdates = readInput("Day05updates")
    part1(inputRules, inputUpdates).println()
    part2(inputRules, inputUpdates).println()
}

