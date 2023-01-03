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

    fun detectStartOfPacketMarker(datastream: String): Int {
        return detectMarker(datastream, 4)
    }

    fun detectMarker(datastream: String, markerSize: Int): Int {
        val slices = datastream.windowed(markerSize)
        for ((index, slice) in slices.withIndex()) {
            if(slice.toSet().size == markerSize) {
                return index + markerSize
            }
        }
        return 0
    }

    override fun part1(input: List<String>): Int {
        return detectStartOfPacketMarker(input.get(0))
    }

    override fun part2(input: List<String>): Int {
        return detectMarker(input.get(0),14)
    }
}

fun main() {
    val day = Day06()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 7)
    check(day.detectStartOfPacketMarker("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(day.detectStartOfPacketMarker("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(day.detectStartOfPacketMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(day.detectStartOfPacketMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    val input = readInput(day.index)
    day.part1(input).println()

    check(day.detectMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb",14) == 19)
    check(day.detectMarker("bvwbjplbgvbhsrlpgdmjqwftvncz",14) == 23)
    check(day.detectMarker("nppdvjthqldpwncqszvftbrmjlhg",14) == 23)
    check(day.detectMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",14) == 29)
    check(day.detectMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",14) == 26)

    day.part2(input).println()
}
