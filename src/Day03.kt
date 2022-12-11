//--- Day 3: Rucksack Reorganization ---
fun leftComponent(rucksack: String) : String {
    return rucksack.substring(0, rucksack.length/2)
}

fun rightComponent(rucksack: String) : String {
    return rucksack.substring(rucksack.length/2)
}

fun getOverlap(aString: String, anotherString: String) : Char {
    val overlap = aString.toSet().intersect(anotherString.toSet())
    if (overlap.isEmpty()) return '0'
    return overlap.first()
}

fun getPrio(item: Char) : Int {
    if (item.equals('0')) return 0
    if (item.isLowerCase()) {
        return item.code - 'a'.code + 1
    } else {
        return item.code - 'A'.code + 27
    }
}

fun main() {
    fun part1(input: List<String>): Int {
//        for(line in input) {
//            val prio = getOverlap(leftComponent(line), rightComponent(line))
//            println("${prio} ${getPrio(prio)}")
//        }
        return input.map { getOverlap(leftComponent(it), rightComponent(it)) }.sumOf { getPrio(it) }
    }

    fun part2(input: List<String>): Int {
        var result = 0
        for(i in 0..input.size - 2 step 3) {
            val elf1 = input[i].toSet()
            val elf2 = input[i + 1].toSet()
            val elf3 = input[i + 2].toSet()
            val overlap = elf1.intersect(elf2).intersect(elf3)
            if (overlap.isNotEmpty()) {
                result += getPrio(overlap.first())
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    part1(testInput).println()
    check(part1(testInput) == 157)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()


}
