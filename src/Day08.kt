data class Coordinates(val x: Int, val y: Int) {
    operator fun plus(other : Coordinates) : Coordinates = Coordinates(x + other.x, y + other.y)
    operator fun minus(other : Coordinates) : Coordinates = Coordinates(x - other.x, y - other.y)
    fun inverse() : Coordinates = Coordinates(-x,-y)

    override fun toString(): String {
        return "($x, $y)"
    }
}

fun main() {

    fun part1(input: List<String>) : Int {
        val grid = input.map { it.toCharArray().asList() }
        val gridHeight = grid.size
        val gridWidth = grid[0].size
        val antiNodes = MutableList(gridHeight) { MutableList(gridWidth) { false } }

        val frequencies = grid
            .flatMapIndexed { vertical, row ->
                row.mapIndexedNotNull{ horizontal, char ->
                    if(char != '.') char to Coordinates(horizontal, vertical) else null
                }
            }
            .groupBy({ (char, _) -> char }, { (_, coordinates) -> coordinates })

        frequencies.forEach { (frequency, coordinates) ->
            val coordinatePairs = mutableListOf<Pair<Coordinates, Coordinates>>()
            for (i in coordinates.indices) {
                for (j in i + 1 until coordinates.size) {
                    coordinatePairs.add(coordinates[i] to coordinates[j])
                }
            }

            coordinatePairs.forEach { (a,b) ->
                val vector = Coordinates(b.x-a.x, b.y-a.y)
                val antiNode1 = b + vector
                val antiNode2 = a + vector.inverse()

                listOf(antiNode1, antiNode2).filter {
                    it.x in 0..<gridWidth && it.y in 0..<gridHeight
                }.forEach {
                    antiNodes[it.y][it.x] = true
                }
            }

        }

        antiNodes.map { it.map{ if (it == true) '#' else '.'}.joinToString("") }.forEach { println(it) }
        return antiNodes.sumOf { it.count { it } }
    }

    fun part2(input: List<String>) : Int {
        return 0
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
