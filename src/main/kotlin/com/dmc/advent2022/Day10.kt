package com.dmc.advent2022

//--- Day 10: Cathode-Ray Tube ---
class Day10 : Day<Int> {
    override val index = 10

    fun parseInput(input: List<String>) : List<Int> =
        buildList {
            add(1) // start value
            input.forEach{ line ->
                when {
                    line.startsWith("noop") -> { add(0) }
                    line.startsWith("addx") -> {
                        add(0) // First cycle there is no change
                        add(line.substringAfter(" ").toInt()) // Second cycle the change is done
                    }
                    else -> { /*nothing*/ }
                }
            }
        }

    override fun part1(input: List<String>): Int {
        val parsedInput = parseInput(input)
        val signals = parsedInput.runningReduce(Int::plus)

        return signals.signalStrengths(20,40).sum()
    }

    fun part2AsString(input: List<String>): String {
        val parsedInput = parseInput(input)
        val signals = parsedInput.runningReduce(Int::plus)

        val pixels = signals.toPixels().map{ if(it) "#" else "."}.joinToString("")

        return pixels.drawGrid(40)
    }

    private fun String.drawGrid(width: Int) : String =
        this.windowed(width, width, false).joinToString(separator = "\n")

    override fun part2(input: List<String>): Int {
        // Not implemented
        return 0
    }
}

fun List<Int>.signalStrengths(start: Int, stepSize: Int): List<Int> =
    (start..this.size step stepSize).map { cycle -> cycle * this[cycle - 1] }

fun List<Int>.toPixels(): List<Boolean> =
    this.mapIndexed { index, signal -> (index % 40) in (signal-1..signal+1) }


fun main() {
    val day = Day10()

    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 13140)

    val input = readInput(day.index)
    day.part1(input).println()

    day.part2AsString(testInput).println()
    day.part2AsString(input).println()

}
