// --- Day 1: Calorie Counting ---

class Day01 : Day<Int> {
    override val index = 1

    override fun part1(input: List<String>): Int {
        val calories = determineCalories(input)
        return calories.max()
    }

    override fun part2(input: List<String>): Int {
        val calories = determineCalories(input)
        val bestThree = calories.sortedDescending().take(3)
        return bestThree.sum()
    }

    private fun determineCalories(input : List<String>): List<Int> {
        val calories: MutableList<Int> = mutableListOf()
        val currentElf: MutableList<Int> = mutableListOf()
        for(line:String in input) {
            if(line.isNotEmpty()) {
                currentElf.add(line.toInt())
            } else {
                calories.add(currentElf.sum())
                currentElf.clear()
            }
        }
        calories.add(currentElf.sum())
        return calories
    }
}


fun main() {
    val day = Day01()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 24000)

    val input = readInput(day.index)
    day.part1(input).println()
    day.part2(input).println()
}
