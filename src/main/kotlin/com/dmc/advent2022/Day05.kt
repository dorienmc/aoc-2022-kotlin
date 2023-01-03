// --- Day 5: Supply Stacks ---
package com.dmc.advent2022

class Day05 : Day<String> {
    override val index = 5
    var crates = Crates(mutableListOf())

    fun setCrates(input: List<String>) {
        crates = Crates(input.toMutableList())
    }

    fun readLine(line: String) : Triple<Int, Int, Int> {
        val (lsh, rhs) = line.split(" from ")
        val (_,moveNum) = lsh.split(" ")
        val (fromS, toS) = rhs.split(" to ")

        return Triple(moveNum.toInt(),fromS.toInt(),toS.toInt())
    }

    override fun part1(input: List<String>): String {
        input.filter{ it.isNotEmpty()}.forEach { line ->
            val (moveNum, fromStack, toStack) = readLine(line)
//            println("moving ${moveNum} crates from stack ${fromStack} to stack $toStack")
            for (i in 1..moveNum) {
                crates.moveCrate(fromStack,toStack)
//                println(cratesEx.getCratesOnTop())
            }
        }

        return crates.getCratesOnTop()
    }

    override fun part2(input: List<String>): String {
        input.filter{ it.isNotEmpty()}.forEach { line ->
            val (moveNum, fromStack, toStack) = readLine(line)
            crates.moveCrate(fromStack, toStack, moveNum)
        }
        return crates.getCratesOnTop()
    }
}

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
