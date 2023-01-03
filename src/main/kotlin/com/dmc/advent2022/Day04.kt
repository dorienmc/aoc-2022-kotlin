// --- Day 4: Camp Cleanup ---
package com.dmc.advent2022

class Day04 : Day<Int> {
    override val index = 4

    override fun part1(input: List<String>): Int {
        return input.map{ it.asRanges() }.count { (left, right) -> left fullyOverlaps right }
    }

    override fun part2(input: List<String>): Int {
        return input.map{ it.asRanges()}.count { (left, right) -> left overlaps right }
    }
}

fun String.toRangeSet() : Set<Int> {
    return (this.substringBefore('-').toInt()..this.substringAfter('-').toInt()).toMutableSet()
}

fun String.asRanges() : Pair<Set<Int>,Set<Int>> =
    this.substringBefore(",").toRangeSet() to this.substringAfter(",").toRangeSet()


infix fun Set<Int>.fullyOverlaps(other: Set<Int>) : Boolean {
    val interSet = this.intersect(other)
    return (interSet.size == this.size || interSet.size == other.size)
}

infix fun Set<Int>.overlaps(other: Set<Int>) : Boolean=  this.intersect(other).isNotEmpty()

fun main() {
    val day = Day04()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 2)

    val input = readInput(day.index)
    day.part1(input).println()
    day.part2(input).println()
}
