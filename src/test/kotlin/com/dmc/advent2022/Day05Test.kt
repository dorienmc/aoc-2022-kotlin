/**
 * @author Dorien Lorijn
 */

package com.dmc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 5")
internal class Day05Test {
    val day: Day05 = Day05()
    var testInput: List<String> = listOf()
    var input: List<String> = listOf()
    val exampleCrates = listOf("ZN","MCD","P");
    val exerciseCrates = listOf("CZNBMWQV","HZRWCB","FQRJ","ZSWHFNMT","GFWLNQP","LPW","VBDRGCQJ","ZQNBW","HLFCGTJ");


    @BeforeEach
    fun setUp() {
        testInput = readInput(day.index, true)
        input = readInput(day.index)
    }

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches example`() {
            // Act
            day.setCrates(exampleCrates)
            val answer = day.part1(testInput)

            // Assert
            assertThat(answer).isEqualTo("CMZ")
        }

        @Test
        fun `Actual answer`() {
            // Act
            day.setCrates(exerciseCrates)
            val answer = day.part1(input)

            // Assert
            assertThat(answer).isEqualTo("QNHWJVJZW")
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches example`() {
            // Act
            day.setCrates(exampleCrates)
            val answer = day.part2(testInput)

            // Assert
            assertThat(answer).isEqualTo("MCD")
        }

        @Test
        fun `Actual answer`() {
            // Act
            day.setCrates(exerciseCrates)
            val answer = day.part2(input)

            // Assert
            assertThat(answer).isEqualTo("BPCZJLFJW")
        }
    }
}
