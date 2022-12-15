// --- Day X: ??? ---

fun main() {

    fun line2Set(rangeString: String) : Set<Int> {
        var lbound = rangeString.split("-")[0].toInt()
        var rbound = rangeString.split("-")[1].toInt()
        return (lbound..rbound).toMutableSet()
    }

    fun isFullIntersect(elf1: Set<Int>, elf2: Set<Int>) : Boolean {
        var interSet = elf1.intersect(elf2)
        return (interSet.size == elf1.size || interSet.size == elf2.size)
    }

    fun isIntersect(elf1: Set<Int>, elf2: Set<Int>) : Boolean{
        var interSet = elf1.intersect(elf2)
        return interSet.isNotEmpty()
    }

    fun part1(input: List<String>): Int {
        return input.filter { line ->
            isFullIntersect(line2Set(line.split(",")[0]), line2Set(line.split(",")[1]))
        }.count()
    }

    fun part2(input: List<String>): Int {
        return input.filter { line ->
            isIntersect(line2Set(line.split(",")[0]), line2Set(line.split(",")[1]))
        }.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(4,true)
    check(part1(testInput) == 2)

    val input = readInput(4)
    part1(input).println()
    part2(input).println()
}
