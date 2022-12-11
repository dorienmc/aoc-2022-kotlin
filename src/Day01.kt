// --- Day 1: Calorie Counting ---

fun determineCalories(input : List<String>): List<Int> {
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


fun main() {
    fun part1(input: List<String>): Int {
        val calories = determineCalories(input)
        return calories.max()
    }

    fun part2(input: List<String>): Int {
        val calories = determineCalories(input)
        val bestThree = calories.sortedDescending().take(3)
        return bestThree.sum()
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
