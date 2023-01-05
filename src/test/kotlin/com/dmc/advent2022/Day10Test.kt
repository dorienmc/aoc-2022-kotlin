/**
 * @author Dorien Lorijn
 */

package com.dmc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 10")
internal class Day10Test {
    val day: Day10 = Day10()
    var testInput: List<String> = listOf()
    var input: List<String> = listOf()

    @BeforeEach
    fun setUp() {
        input = readInput(day.index)
        testInput = readInput(day.index, true)
    }

    @Nested
    @DisplayName("Helper methods")
    inner class HelperMethods {
        @Test
        fun parseInput() {
            val input = """
                addx 2
                noop 
                addx 3
            """.trimIndent().split("\n")

            val parsedInput = day.parseInput(input)
            assertThat(parsedInput.size).isEqualTo(6)
            assertThat(parsedInput).containsExactly(1,0,2,0,0,3)

            val input2 = """
                noop
                addx 3
                addx -5
            """.trimIndent().split("\n")
            assertThat(day.parseInput(input2)).containsExactly(1,0,0,3,0,-5)
        }

        @Test
        fun runningReduce() {
            val input = """
                addx 2
                noop 
                addx 3
            """.trimIndent().split("\n")
            val signals = day.parseInput(input).runningReduce(Int::plus)

            assertThat(signals).containsExactly(1,1,3,3,3,6)

            val input2 = """
                noop
                addx 3
                addx -5
            """.trimIndent().split("\n")
            val signals2 = day.parseInput(input2).runningReduce(Int::plus)
            assertThat(signals2).containsExactly(1,1,1,4,4,-1)
        }

        @Test
        fun Signals() {
            val signals = day.parseInput(testInput).runningReduce(Int::plus)

            // Signals contains value at end of cycle, we want to check value at start of cycle
            assertThat(signals[20-1]).isEqualTo(21)
            assertThat(signals[60-1]).isEqualTo(19)
            assertThat(signals[100-1]).isEqualTo(18)
            assertThat(signals[140-1]).isEqualTo(21)
            assertThat(signals[180-1]).isEqualTo(16)
            assertThat(signals[190-1]).isEqualTo(28)
            assertThat(signals[200-1]).isEqualTo(38)
            assertThat(signals[210-1]).isEqualTo(-1)
            assertThat(signals[220-1]).isEqualTo(18)
        }

        @Test
        fun sampleSignals() {
            val signals = day.parseInput(testInput).runningReduce(Int::plus)
            val sampleSignals = signals.signalStrengths(20,40)

            assertThat(sampleSignals).containsExactly(420, 1140, 1800, 2940, 2880, 3960)
        }
    }

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches example`() {
            // Act
            val answer = day.part1(testInput)

            // Assert
            assertThat(answer).isEqualTo(13140)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part1(input)

            // Assert
            assertThat(answer).isEqualTo(14860)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches example`() {
            // Act
            val answer = day.part2AsString(testInput)
            val expected = """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
            """.trimIndent()

            // Assert
            assertThat(answer).isEqualTo(expected)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part2AsString(input)
            val expected = """
                ###...##..####.####.#..#.#..#.###..#..#.
                #..#.#..#....#.#....#..#.#..#.#..#.#.#..
                #..#.#......#..###..####.#..#.#..#.##...
                ###..#.##..#...#....#..#.#..#.###..#.#..
                #.#..#..#.#....#....#..#.#..#.#.#..#.#..
                #..#..###.####.####.#..#..##..#..#.#..#.
            """.trimIndent()

            // Assert
            assertThat(answer).isEqualTo(expected)
        }
    }
}
