// --- Day X: ??? ---

class Crates(var crates: MutableList<String>) {

    fun moveCrate(stackFrom: Int, stackTo: Int, n: Int = 1) {
        val crate = popCrate(stackFrom, n)
        addCrates(stackTo, crate)
    }

    fun popCrate(stackId: Int, n: Int = 1): String {
        val stack = crates[stackId - 1]
        val crate = stack.substring(stack.length - n)
        crates[stackId - 1] = stack.dropLast(n)
        return crate
    }

    fun addCrates(stackId: Int, crate: String) {
        val stack = crates[stackId - 1]
        crates[stackId - 1] = stack.plus(crate)
    }

    fun getCratesOnTop() : String {
        return crates.map {
            if(it.isNotEmpty()) {
                it.last()
            } else {
                ""
            }
        }.joinToString("")
    }
}


fun main() {

    val cratesEx = Crates(listOf("ZN","MCD","P").toMutableList())
    var cratesExercise = Crates(listOf("CZNBMWQV","HZRWCB","FQRJ","ZSWHFNMT","GFWLNQP","LPW","VBDRGCQJ","ZQNBW","HLFCGTJ").toMutableList())

    /*
[V]         [T]         [J]
[Q]         [M] [P]     [Q]     [J]
[W] [B]     [N] [Q]     [C]     [T]
[M] [C]     [F] [N]     [G] [W] [G]
[B] [W] [J] [H] [L]     [R] [B] [C]
[N] [R] [R] [W] [W] [W] [D] [N] [F]
[Z] [Z] [Q] [S] [F] [P] [B] [Q] [L]
[C] [H] [F] [Z] [G] [L] [V] [Z] [H]
     */

    fun part1(input: List<String>, crates: Crates): String {
        input.filter{ it.isNotEmpty()}.forEach { line ->
            val moveNum = line.split(" from ")[0].split(" ")[1].toInt()
            val fromStack = line.split(" from ")[1].split(" to ")[0].toInt()
            val toStack = line.split(" from ")[1].split(" to ")[1].toInt()
//            println("moving ${moveNum} crates from stack ${fromStack} to stack $toStack")
            for (i in 1..moveNum) {
                crates.moveCrate(fromStack,toStack)
//                println(cratesEx.getCratesOnTop())
            }
        }

        return crates.getCratesOnTop()
    }

    fun part2(input: List<String>, crates: Crates): String {
        input.filter{ it.isNotEmpty()}.forEach { line ->
            val moveNum = line.split(" from ")[0].split(" ")[1].toInt()
            val fromStack = line.split(" from ")[1].split(" to ")[0].toInt()
            val toStack = line.split(" from ")[1].split(" to ")[1].toInt()
            crates.moveCrate(fromStack, toStack, moveNum)
        }
        return crates.getCratesOnTop()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(5,true)
    check(part1(testInput, cratesEx) == "CMZ")
    check(part2(testInput, Crates(listOf("ZN","MCD","P").toMutableList())) == "MCD")

    val input = readInput(5)
    part1(input,cratesExercise).println()
    cratesExercise = Crates(listOf("CZNBMWQV","HZRWCB","FQRJ","ZSWHFNMT","GFWLNQP","LPW","VBDRGCQJ","ZQNBW","HLFCGTJ").toMutableList())
    part2(input,cratesExercise).println()

}

/*
[V]         [T]         [J]
[Q]         [M] [P]     [Q]     [J]
[W] [B]     [N] [Q]     [C]     [T]
[M] [C]     [F] [N]     [G] [W] [G]
[B] [W] [J] [H] [L]     [R] [B] [C]
[N] [R] [R] [W] [W] [W] [D] [N] [F]
[Z] [Z] [Q] [S] [F] [P] [B] [Q] [L]
[C] [H] [F] [Z] [G] [L] [V] [Z] [H]
 1   2   3   4   5   6   7   8   9


 */
