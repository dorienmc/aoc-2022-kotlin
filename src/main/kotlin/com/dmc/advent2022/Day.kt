// --- Day X: ??? ---
package com.dmc.advent2022

interface Day<T> {
    val index: Int

    fun part1(input: List<String>): T

    fun part2(input: List<String>): T
}

fun main() {
    class ADay: Day<Int> {
        override val index = 1
        override fun part1(input: List<String>): Int {
            return 0
        }

        override fun part2(input: List<String>): Int {
            return 0
        }

    }
    val day = ADay()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 0)

    val input = readInput(day.index)
    day.part1(input).println()
    day.part2(input).println()
}
