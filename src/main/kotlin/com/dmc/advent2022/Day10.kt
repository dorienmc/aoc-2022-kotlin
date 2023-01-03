package com.dmc.advent2022
import kotlin.math.min

//--- Day 10: Cathode-Ray Tube ---
class Day10 : Day<Int> {
    override val index = 10

    override fun part1(input: List<String>): Int {
        var cycleCount = 1
        val xValues = mutableMapOf(1 to 1)
        var x = 1

        for(line in input) {
            xValues[cycleCount + 1] = x
            if (line == "noop") {
                cycleCount += 1
            } else {
                val addx = line.split(" ")[1].toInt()
                xValues[cycleCount + 2] = x + addx
                cycleCount += 2
                x += addx
            }
//            println("cycle ${cycleCount}, x: $x")
        }

        for(i in 20..cycleCount step 40) {
            println("cycle ${i}, x: ${xValues[i]}, signal strength: ${i * xValues.getOrDefault(i,0)}")
        }
        return (20..cycleCount step 40).sumOf { i -> i * xValues.getOrDefault(i, 0) }
    }

    override fun part2(input: List<String>): Int {
        var cycleCount = 1
        val xValues = mutableMapOf(1 to 1)
        var pixels = "#"
        var x = 1

        for(line in input) {
            xValues[cycleCount + 1] = x
            pixels += drawPixel(cycleCount + 1, x)
//            println("cycle ${cycleCount + 1}, x: $x, pixel: ${pixels.last()}")

            if (line == "noop") {
                cycleCount += 1
            } else {
                val addx = line.split(" ")[1].toInt()
                xValues[cycleCount + 2] = x + addx
                pixels += drawPixel(cycleCount + 2, x + addx)
//                println("cycle ${cycleCount + 2}, x: ${x+addx}, pixel: ${pixels.last()}")
                cycleCount += 2
                x += addx
            }

//            println("cycle ${cycleCount}, x: $x")
        }

        for(i in 0..5) {
            val s = i*40
            val e = min(pixels.length - 1, (i*40)+39)
            println(pixels.substring(s,e))
        }

        return 0
    }

    fun drawPixel(cycleCount: Int, xval: Int): String {
        return when((cycleCount - 1) % 40) {
            in (xval-1 .. xval+1) -> "#"
            else -> "."
        }
    }
}

fun main() {
    val day = Day10()

    // test if implementation meets criteria from the description, like:
//    val testInput1 = listOf("noop", "addx 3", "addx -5")
//    day.part1(testInput1)

    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 13140)

    val input = readInput(day.index)
    day.part1(input).println()

    day.part2(testInput)
    day.part2(input).println()

}
