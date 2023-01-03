/**
 * @author Dorien Lorijn
 */

package com.dmc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


@DisplayName("Day 6")
internal class Day06Test {
    val day: Day06 = Day06()
    var testInput: List<String> = listOf()
    var input: List<String> = listOf()

    @BeforeEach
    fun setUp() {
        testInput = readInput(day.index, true)
        input = readInput(day.index)
    }

    @Nested
    @DisplayName("Detect start of packet marker")
    inner class HelperMethods {
        @ParameterizedTest
        @CsvSource(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb, 4, 7",
            "bvwbjplbgvbhsrlpgdmjqwftvncz, 4, 5",
            "nppdvjthqldpwncqszvftbrmjlhg, 4, 6",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 4, 10",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw, 4, 11",
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb, 14, 19",
            "bvwbjplbgvbhsrlpgdmjqwftvncz, 14, 23",
            "nppdvjthqldpwncqszvftbrmjlhg, 14, 23",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 14, 29",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw, 14, 26"
        )
        fun `Should detect start of packet marker correctly`(packet: String, markerSize: Int, expected: Int) {
            assertThat(packet.detectMarker(markerSize)).isEqualTo(expected)
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
            assertThat(answer).isEqualTo(7)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part1(input)

            // Assert
            assertThat(answer).isEqualTo(1109)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches example`() {
            // Act
            val answer = day.part2(testInput)

            // Assert
            assertThat(answer).isEqualTo(19)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part2(input)

            // Assert
            assertThat(answer).isEqualTo(3965)
        }
    }
}
