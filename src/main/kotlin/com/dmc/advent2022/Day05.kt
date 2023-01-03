// --- Day 5: Supply Stacks ---
package com.dmc.advent2022

class Day05 : Day<String> {
    override val index = 5
    var crates = Crates(mutableListOf())

    fun setCrates(input: List<String>) {
        crates = Crates(input.toMutableList())
    }

    private fun String.parseInstruction() : Instruction {
        val (lsh, rhs) = this.split(" from ")
        val (_,moveNum) = lsh.split(" ")
        val (fromS, toS) = rhs.split(" to ")

        return Instruction(moveNum.toInt(),fromS.toInt(),toS.toInt())
    }

    override fun part1(input: List<String>): String {
        input
            .filter{ it.isNotEmpty()}
            .map{ it.parseInstruction() }
            .forEach { (amount, source, target) ->
                repeat(amount) {
                    crates.moveCrate(source, target)
                }
            }

        return crates.getCratesOnTop()
    }

    override fun part2(input: List<String>): String {
        input.filter{ it.isNotEmpty()}.map{ it.parseInstruction()}
            .forEach { (amount, source, target) ->
                crates.moveCrate(source, target, amount)
            }
        return crates.getCratesOnTop()
    }
}

data class Instruction(val amount: Int, val source: Int, val target: Int)

class Crates(var crates: MutableList<String>) {

    fun moveCrate(stackFrom: Int, stackTo: Int, n: Int = 1) {
        val crate = popCrate(stackFrom, n)
        addCrates(stackTo, crate)
    }

    private fun popCrate(stackId: Int, n: Int = 1): String {
        val stack = crates[stackId - 1]
        val crate = stack.substring(stack.length - n)
        crates[stackId - 1] = stack.dropLast(n)
        return crate
    }

    private fun addCrates(stackId: Int, crate: String) {
        val stack = crates[stackId - 1]
        crates[stackId - 1] = stack.plus(crate)
    }

    fun getCratesOnTop() : String {
        return crates.mapNotNull { it.lastOrNull() }.joinToString("")
    }
}

val exampleCrates = listOf("ZN","MCD","P")
val exerciseCrates = listOf("CZNBMWQV","HZRWCB","FQRJ","ZSWHFNMT","GFWLNQP","LPW","VBDRGCQJ","ZQNBW","HLFCGTJ")

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

fun main() {
    val day = Day05()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    day.setCrates(exampleCrates)
    check(day.part1(testInput) == "CMZ")
    day.setCrates(exampleCrates)
    check(day.part2(testInput) == "MCD")

    val input = readInput(day.index)
    day.setCrates(exerciseCrates)
    day.part1(input).println()
    day.setCrates(exerciseCrates)
    day.part2(input).println()
}
