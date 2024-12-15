import java.math.BigInteger
import kotlin.io.path.Path
import kotlin.io.path.readText

fun String.splitInHalf(): Pair<String, String> {
    val middle = this.length / 2
    return this.substring(0, middle) to this.substring(middle)
}

class MagicStone private constructor(val engraving: String) {

    private val blinkTransform: List<MagicStone> by lazy {
        when {
            this == ZERO -> {
                listOf(get("1"))
            }
            engraving.length % 2 == 0 -> {
                engraving.splitInHalf().toList().map { it.trimStart('0') }.map {
                    when (it) {
                        "" -> ZERO
                        else -> get(it)
                    }
                }
            }
            else -> {
                listOf(get("${2024L * engraving.toLong()}"))
            }
        }
    }

    fun blinkTransform(): List<MagicStone> = blinkTransform

    companion object {
        private val cache = mutableMapOf<String, MagicStone>()

        fun get(engraving: String): MagicStone {
            return cache.getOrPut(engraving) { MagicStone(engraving) }
        }

        val ZERO = get("0")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MagicStone
        return engraving == other.engraving
    }

    override fun hashCode(): Int = engraving.hashCode()
}

class MagicStones(initialStones: List<MagicStone>) {
    // number of occurrences of each stone
    private var stonesFrequency = initialStones
        .groupingBy { it }
        .eachCount()
        .mapValues { (_, count) -> BigInteger.valueOf(count.toLong()) }
        .toMutableMap()

    fun blink(numBlinks: Int) {
        val startTime = System.currentTimeMillis()
        repeat(numBlinks) {
            val newStonesFrequency = mutableMapOf<MagicStone, BigInteger>()

            // transform each existing stone per the blink rules & compute new counts
            for ((existingStone, count) in stonesFrequency) {
                val newStones = existingStone.blinkTransform()
                for (newStone in newStones) {
                    newStonesFrequency.compute(newStone) { _, currentCount ->
                        (currentCount ?: BigInteger.ZERO).add(count)
                    }
                }
            }

            stonesFrequency = newStonesFrequency
        }
        println("Blinked $numBlinks times in ${System.currentTimeMillis() - startTime} ms")
    }

    fun count() : BigInteger {
        return stonesFrequency.values.fold(BigInteger.ZERO) { acc, count -> acc.add(count) }
    }
}

fun main() {
    fun part1(input: String): BigInteger {
        return MagicStones(input.split(" ").map(MagicStone::get)).apply { blink(25) }.count()
    }

    fun part2(input: String): BigInteger {
        return MagicStones(input.split(" ").map(MagicStone::get)).apply { blink(75) }.count()
    }

    val input = Path("src/Day11.txt").readText().trim()
    println(part1(input))
    println(part2(input))
}
