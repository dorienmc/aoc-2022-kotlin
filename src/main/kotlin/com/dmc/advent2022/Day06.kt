// --- Day 6: Tuning Trouble ---
/*
To fix the communication system, you need to add a subroutine to the device
that detects a start-of-packet marker in the datastream. In the protocol
being used by the Elves, the start of a packet is indicated by a sequence
of four characters that are all different.
 */
package com.dmc.advent2022

class Day06 : Day<Int> {
    override val index = 6

    override fun part1(input: List<String>): Int {
        return input[0].detectMarker()
    }

    override fun part2(input: List<String>): Int {
        return input[0].detectMarker(14)
    }
}

fun String.detectMarker(markerSize: Int = 4): Int {
    return this
        .windowed(markerSize)
        .withIndex()
        .first { (_, slice) -> slice.toSet().size == markerSize }
        .index + markerSize
}

fun main() {
    val day = Day06()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 7)
    check("bvwbjplbgvbhsrlpgdmjqwftvncz".detectMarker() == 5)
    check("nppdvjthqldpwncqszvftbrmjlhg".detectMarker() == 6)
    check("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".detectMarker() == 10)
    check("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".detectMarker() == 11)

    val input = readInput(day.index)
    day.part1(input).println()

    check("mjqjpqmgbljsphdztnvjfqwrcgsmlb".detectMarker(14) == 19)
    check("bvwbjplbgvbhsrlpgdmjqwftvncz".detectMarker(14) == 23)
    check("nppdvjthqldpwncqszvftbrmjlhg".detectMarker(14) == 23)
    check("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".detectMarker(14) == 29)
    check("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".detectMarker(14) == 26)

    day.part2(input).println()
}
