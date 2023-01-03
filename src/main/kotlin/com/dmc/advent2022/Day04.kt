// --- Day 4: Camp Cleanup ---
package com.dmc.advent2022

class Day04 : Day<Int> {
    override val index = 4

    override fun part1(input: List<String>): Int {
        return input.filter { line ->
            isFullIntersect(line2Set(line.split(",")[0]), line2Set(line.split(",")[1]))
        }.count()
    }

    override fun part2(input: List<String>): Int {
        return input.filter { line ->
            isIntersect(line2Set(line.split(",")[0]), line2Set(line.split(",")[1]))
        }.count()
    }
}

fun line2Set(rangeString: String) : Set<Int> {
    val lbound = rangeString.split("-")[0].toInt()
    val rbound = rangeString.split("-")[1].toInt()
    return (lbound..rbound).toMutableSet()
}

fun isFullIntersect(elf1: Set<Int>, elf2: Set<Int>) : Boolean {
    val interSet = elf1.intersect(elf2)
    return (interSet.size == elf1.size || interSet.size == elf2.size)
}

fun isIntersect(elf1: Set<Int>, elf2: Set<Int>) : Boolean{
    val interSet = elf1.intersect(elf2)
    return interSet.isNotEmpty()
}

fun main() {
    val day = Day04()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 2)

    val input = readInput(day.index)
    day.part1(input).println()
    day.part2(input).println()
}
