import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readText

abstract class FileSystemBlock() {}
class FileBlock(val id: Int) : FileSystemBlock() {
    override fun toString(): String {
        return id.toString()
    }
}
class FreeSpaceBlock : FileSystemBlock() {
    override fun toString(): String {
        return "."
    }
}


fun main() {
    fun parseInput(input: String) : LinkedList<FileSystemBlock> {
        return LinkedList<FileSystemBlock>().apply {
            input.toCharArray().forEachIndexed { index, char ->
                addAll(List(char.digitToInt()) {
                    if (index % 2 == 0) {
                        FileBlock(id = index / 2)
                    } else {
                        FreeSpaceBlock()
                    }
                })
            }
        }
    }

    fun part1(input: String) : Long {
        val blocks = parseInput(input)
        var firstFreeBlock = blocks.indexOfFirst { it is FreeSpaceBlock }
        while(firstFreeBlock != -1) {
            var block : FileSystemBlock = FreeSpaceBlock()
            while(block is FreeSpaceBlock) {
                block = blocks.removeLast()
            }
            blocks[firstFreeBlock] = block
            firstFreeBlock = blocks.indexOfFirst { it is FreeSpaceBlock }
        }

        return blocks.mapIndexed { index, fileSystemBlock ->
            if(fileSystemBlock is FileBlock) {
                index.toLong() * fileSystemBlock.id.toLong()
            }
            else {
                0L
            }
        }.sum()
    }

    fun part2(input: String) : Int {
        return 0
    }

    val input = Path("src/Day09.txt").readText().trim()
    part1(input).println()
    part2(input).println()
}
