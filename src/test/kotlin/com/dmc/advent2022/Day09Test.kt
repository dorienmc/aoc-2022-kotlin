/**
 * @author Dorien Lorijn
 */

package com.dmc.advent2022

import org.assertj.core.api.Assertions.`as`
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 9")
internal class Day09Test {
    val day: Day09 = Day09()
    var testInput: List<String> = listOf()
    var input: List<String> = listOf()

    @BeforeEach
    fun setUp() {
        input = readInput(day.index)
    }

    @Nested
    @DisplayName("Point")
    inner class PointTester {
        @Test
        fun `Creating a point`() {
            assertThat(Point(42,54).toString()).isEqualTo("(42, 54)")
        }

        @Test
        fun `Moving around`() {
            val p = Point(5,5)

            // Direction should be U,D,R,L other wise no moving
            assertThat(p.move("wronginput")).isEqualTo(Point(5,5))

            assertThat(p.move("U")).isEqualTo(Point(5,6))
            assertThat(p.move("D")).isEqualTo(Point(5,4))
            assertThat(p.move("R")).isEqualTo(Point(6,5))
            assertThat(p.move("L")).isEqualTo(Point(4,5))
        }

        @Test
        fun `Are points touching`() {
            val p = Point()

            // Touches when overlapping
            assertThat(p.touches(Point()))

            // Touches when side by side
            assertThat(p.touches(Point(1,0)))
            assertThat(p.touches(Point(-1,0)))
            assertThat(p.touches(Point(0,1)))
            assertThat(p.touches(Point(0,-1)))

            // Touches diagonally
            assertThat(p.touches(Point(1,1)))
            assertThat(p.touches(Point(-1,-1)))
            assertThat(p.touches(Point(-1,1)))
            assertThat(p.touches(Point(1,-1)))

            // Otherwise not touching
            assertThat(p.touches(Point(0,2)))
            assertThat(p.touches(Point(2,0)))
        }

        @Test
        fun `Move towards`() {
            // Move from (0,0) towards (1,0)
            assertThat(Point(0,0).moveTowards(Point(1,0))).isEqualTo(Point(1,0))

            // Move from (0,0) towards (1,2)
            assertThat(Point(0,0).moveTowards(Point(1,2))).isEqualTo(Point(1,1))
        }

        @Test
        fun `Move and follow`() {
            var head = Point()
            var tail = Point()

            // R4
            head = head.move("R")
            assertThat(head).isEqualTo(Point(1,0))
            assertThat(head.touches(tail)).isTrue()

            head = head.move("R")
            assertThat(head).isEqualTo(Point(2,0))
            assertThat(head.touches(tail)).isFalse()
            tail = tail.moveTowards(head)
            assertThat(tail).isEqualTo(Point(1,0))

            head = head.move("R")
            assertThat(head).isEqualTo(Point(3,0))
            assertThat(head.touches(tail)).isFalse()
            tail = tail.moveTowards(head)
            assertThat(tail).isEqualTo(Point(2,0))

            head = head.move("R")
            assertThat(head).isEqualTo(Point(4,0))
            assertThat(head.touches(tail)).isFalse()
            tail = tail.moveTowards(head)
            assertThat(tail).isEqualTo(Point(3,0))

            // U4
            head = head.move("U")
            assertThat(head).isEqualTo(Point(4,1))
            assertThat(head.touches(tail)).isTrue()

            head = head.move("U")
            assertThat(head).isEqualTo(Point(4,2))
            assertThat(head.touches(tail)).isFalse()
            tail = tail.moveTowards(head)
            assertThat(tail).isEqualTo(Point(4,1))

            head = head.move("U")
            assertThat(head).isEqualTo(Point(4,3))
            assertThat(head.touches(tail)).isFalse()
            tail = tail.moveTowards(head)
            assertThat(tail).isEqualTo(Point(4,2))

            head = head.move("U")
            assertThat(head).isEqualTo(Point(4,4))
            assertThat(head.touches(tail)).isFalse()
            tail = tail.moveTowards(head)
            assertThat(tail).isEqualTo(Point(4,3))

            // L3
            repeat(3) {
                head = head.move("L")
                if(!head.touches(tail)) {
                    tail = tail.moveTowards(head)
                }
            }
            assertThat(head).isEqualTo(Point(1,4))
            assertThat(tail).isEqualTo(Point(2,4))

            // D1
            head = head.move("D")
            assertThat(head).isEqualTo(Point(1,3))
            assertThat(head.touches(tail)).isTrue()

            // L5
            repeat(5) {
                head = head.move("L")
                if(!head.touches(tail)) {
                    tail = tail.moveTowards(head)
                }
            }
            assertThat(head).isEqualTo(Point(-4,3))
            assertThat(tail).isEqualTo(Point(-3,3))

            // R2
            head = head.move("R")
            assertThat(head).isEqualTo(tail)
            assertThat(head.touches(tail)).isTrue()

            head = head.move("R")
            assertThat(head).isEqualTo(Point(-2,3))
            assertThat(tail).isEqualTo(Point(-3,3))
            assertThat(head.touches(tail)).isTrue()
        }
    }

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches example`() {
            // Act
            testInput = readInput(day.index, true)
            val answer = day.part1(testInput)

            // Assert
            assertThat(answer).isEqualTo(13)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part1(input)

            // Assert
            assertThat(answer).isEqualTo(5683)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches example`() {
            // Act
            testInput = readInput("Day09_test2","src/test/resources")
            val answer = day.part2(testInput)

            // Assert
            assertThat(answer).isEqualTo(36)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part2(input)

            // Assert
            assertThat(answer).isEqualTo(2372)
        }
    }
}
