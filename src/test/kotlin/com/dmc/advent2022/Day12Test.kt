/**
 * Unit tests for Day 12
 * @author Dorien Lorijn
 */

package com.dmc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 12")
internal class Day12Test {
    val day: Day12 = Day12()
    var testInput: List<String> = listOf()
    var input: List<String> = listOf()

    @BeforeEach
    fun setUp() {
        testInput = readInput(day.index, true)
        input = readInput(day.index)
    }

    @Nested
    @DisplayName("Helper methods")
    inner class HelperMethods {
        @Test
        fun parseInput(){
            val heightMap = day.parseInput(testInput)

            assertThat(heightMap.start).isEqualTo(Point2D())
            assertThat(heightMap.end).isEqualTo(Point2D(2,5))
            assertThat(heightMap.elevations[Point2D(0,1)]).isEqualTo(0)
            assertThat(heightMap.elevations[Point2D(0,2)]).isEqualTo(1)
        }

        @Test
        fun friendlyNeighbours() {
            val heightMap = day.parseInput(testInput)

            val answer = heightMap.getFriendlyNeighbours(heightMap.start)
            assertThat(answer).containsExactly(Point2D(0,1), Point2D(1,0))

            assertThat(heightMap.getFriendlyNeighbours(heightMap.end)).containsExactly(
                Point2D(2,4)
            )
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
            assertThat(answer).isEqualTo(31)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part1(input)

            // Assert
            assertThat(answer).isEqualTo(468)
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
            assertThat(answer).isEqualTo(29)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part2(input)

            // Assert
            assertThat(answer).isEqualTo(459)
        }
    }
}

