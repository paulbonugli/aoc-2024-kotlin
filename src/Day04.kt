fun main() {
    // get list of all possible sub-grids of the given size
    fun toSubGrids(input: List<String>, size: Int): List<List<String>> {
        return input.windowed(size).flatMap { rowsWindow ->
            // now window each row to get our columns
            val columnWindows = rowsWindow.map { row -> row.windowed(size) }

            // zip the columns together
            columnWindows[0].indices.map { i ->
                columnWindows.map { it[i] }
            }
        }
    }

    // rotate a grid (so ab,cd turns into ac,bd)
    fun rotate(input: List<String>) : List<String> {
        return  IntRange(0, input[0].lastIndex).map { i ->
            buildString {
                input.forEach { append(it[i]) }
            }
        }
    }

    // does the given string exactly match the desired word, in any order
    fun isMatch(str: String, match: String) : Boolean {
        return str == match || str.reversed() == match
    }

    // get the two possible diagonals from the middle of the given grid (assume reverse this to get the other 2)
    fun getDiagonals(subgrid: List<String>) : List<String> {
        return listOf(subgrid.indices.map { i -> subgrid[i][i]}.joinToString(""),
                      subgrid.indices.map { i -> subgrid[i][subgrid.size - i - 1]}.joinToString(""))
    }

    fun part1(input: List<String>) : Int {
        val words = (input + rotate(input)).flatMap { line -> line.windowed(4) }.toMutableList()
        words += toSubGrids(input, 4).flatMap(::getDiagonals)
        return words.count { word -> isMatch(word, "XMAS") }
    }

    fun part2(input: List<String>) : Int {
        return toSubGrids(input, 3)
            .map(::getDiagonals)
            .count { diagonalWords -> diagonalWords.all { isMatch(it, "MAS") } }
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

