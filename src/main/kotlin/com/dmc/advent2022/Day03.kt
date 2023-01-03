//--- Day 3: Rucksack Reorganization ---
package com.dmc.advent2022

class Day03 : Day<Int> {
    override val index = 3

    override fun part1(input: List<String>): Int {
        return input.sumOf { it.overlap().priority() }
    }

    override fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { it.overlap().priority() }
    }
}

fun String.overlap() : Char {
    return listOf(
        this.substring(0, this.length/2),
        this.substring(this.length/2)).
    overlap()
}

fun List<String>.overlap() : Char {
    return map { it.toSet() }
        .reduce { left, right -> left.intersect(right) }
        .first()
}

fun Char.priority() : Int =
    when (this) {
        '0' -> 0
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> throw IllegalArgumentException("Letter not in range: $this")
    }

fun main() {
    val day = Day03()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 157)

    val input = readInput(day.index)
    day.part1(input).println()
    day.part2(input).println()
}
