package com.dmc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * Unit tests for day 8
 *
 * @author Dorien Lorijn
 */
@DisplayName("Day 8")
internal class Day08Test {
    val day: Day08 = Day08()
    var testInput: List<String> = listOf()
    var input: List<String> = listOf()

    @BeforeEach
    fun setUp() {
        testInput = readInput(day.index, true)
        input = readInput(day.index)
    }

    @Nested
    @DisplayName("Helper Methods")
    inner class HelperMethods {
        @Test
        fun toMatrix() {
            val expected = """
                [3, 0, 3, 7, 3]
                [2, 5, 5, 1, 2]
                [6, 5, 3, 3, 2]
                [3, 3, 5, 4, 9]
                [3, 5, 3, 9, 0]
            """.trimIndent()

            val answer = testInput.toMatrix()

            assertThat(answer.toString()).isEqualTo(expected)
        }

        @Test
        fun lookUp() {
            val grid = testInput.toMatrix()

            assertThat(grid.get(4,1)).isEqualTo(5)
            assertThat(grid.lookUp(4,1,)).isEqualTo(2)

            assertThat(grid.get(0,2)).isEqualTo(3)
            assertThat(grid.lookUp(0,2)).isEqualTo(0)

            assertThat(grid.get(1,1)).isEqualTo(5)
            assertThat(grid.lookUp(1,1)).isEqualTo(1)
        }

        @Test
        fun lookDown() {
            val grid = testInput.toMatrix()

            assertThat(grid.get(1,1)).isEqualTo(5)
            assertThat(grid.lookDown(1,1)).isEqualTo(1)

            assertThat(grid.get(1,4)).isEqualTo(2)
            assertThat(grid.lookDown(1,4)).isEqualTo(1)
        }

        @Test
        fun lookLeft() {
            val grid = testInput.toMatrix()

            assertThat(grid.get(1,1)).isEqualTo(5)
            assertThat(grid.lookLeft(1,1)).isEqualTo(1)

            assertThat(grid.get(1,4)).isEqualTo(2)
            assertThat(grid.lookLeft(1,4)).isEqualTo(2)
        }

        @Test
        fun lookRight() {
            val grid = testInput.toMatrix()

            assertThat(grid.get(1,1)).isEqualTo(5)
            assertThat(grid.lookRight(1,1)).isEqualTo(1)

            assertThat(grid.get(1,4)).isEqualTo(2)
            assertThat(grid.lookRight(1,4)).isEqualTo(0)
        }

        @Test
        fun viewFrom() {
            val grid = testInput.toMatrix()

            val view = grid.viewFrom(2,2)
            assertThat(view.size).isEqualTo(4)
            assertThat(view).containsExactlyElementsOf(listOf(listOf(5,6), listOf(5,3), listOf(3,2), listOf(5,3)))

            val anotherView = grid.viewFrom(0,1)
            assertThat(anotherView).containsExactlyElementsOf(listOf(listOf(3), emptyList(), listOf(3,7,3), listOf(5,5,3,5)))
        }

        @Test
        fun isVisible() {
            val grid = testInput.toMatrix()

            // All trees on the edge are visible
            for(i in 0 until grid.rows) {
                for(j in 0 until grid.cols) {
                    if (i == 0 || j == 0 || i == grid.rows - 1 || j == grid.cols - 1) {
                        assertThat(grid.isVisible(i,j)).describedAs("Expected tree at (%d, %d) to be visible",i,j).isTrue()
                    }
                }
            }

            //The top-left 5 is visible
            assertThat(grid.isVisible(1,1)).describedAs("Expected tree at (1,1) to be visible").isTrue()
            //The top-middle 5 is visible
            assertThat(grid.isVisible(1,2)).describedAs("Expected tree at (1,2) to be visible").isTrue()
            //The top-right 1 is not visible
            assertThat(grid.isVisible(1,3)).describedAs("Expected tree at (1,3) to not be visible").isFalse()
            //The left-middle 5 is visible
            assertThat(grid.isVisible(2,1)).describedAs("Expected tree at (2,1) to be visible").isTrue()
            //The center 3 is not visible
            assertThat(grid.isVisible(2,2)).describedAs("Expected tree at (2,2) to not be visible").isFalse()
            //The right-middle 3 is visible
            assertThat(grid.isVisible(2,3)).describedAs("Expected tree at (2,3) to be visible").isTrue()
            //In the bottom row, the middle 5 is visible, but the 3 and 4 are not.
            assertThat(grid.isVisible(3,1)).describedAs("Expected tree at (3,1) to not be visible").isFalse()
            assertThat(grid.isVisible(3,2)).describedAs("Expected tree at (3,2) to be visible").isTrue()
            assertThat(grid.isVisible(3,3)).describedAs("Expected tree at (3,3) to not be visible").isFalse()
        }

        @Test
        fun scenicScore() {
            val grid = testInput.toMatrix()

            assertThat(day.scenicScore(1,2, grid)).isEqualTo(4)
            assertThat(day.scenicScore(3,2, grid)).isEqualTo(8)

            assertThat(grid.scenicScore(1,2)).isEqualTo(4)
            assertThat(grid.scenicScore(3,2)).isEqualTo(8)
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
            assertThat(answer).isEqualTo(21)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part1(input)

            // Assert
            assertThat(answer).isEqualTo(1827)
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
            assertThat(answer).isEqualTo(8)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part2(input)

            // Assert
            assertThat(answer).isEqualTo(335580)
        }
    }


}
