/**
 * Unit tests for Day 13
 * @author Dorien Lorijn
 */

package com.dmc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 13")
internal class Day13Test {
    val day: Day13 = Day13()
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
        fun `Create package from String`(){

            val p1 = "[10,1,3,1,1]".toPacket()
            assertThat(p1.size()).isEqualTo(5)
            assertThat(p1.toString()).isEqualTo("[10, 1, 3, 1, 1]")

            val p2 = "[[1],[2,3,4]]".toPacket()
            assertThat(p2.size()).isEqualTo(2)
            assertThat(p2.toString()).isEqualTo("[[1], [2, 3, 4]]")

            val p3 = "[[[]]]".toPacket()
            assertThat(p3.size()).isEqualTo(1)
            assertThat(p3.toString()).isEqualTo("[[[]]]")

            val p4 = "[1,[2,[3,[4,[5,6,7]]]],8,9]".toPacket()
            assertThat(p4.size()).isEqualTo(4)
            assertThat(p4.toString()).isEqualTo("[1, [2, [3, [4, [5, 6, 7]]]], 8, 9]")
        }

        @Test
        fun `Compare IntPackets`() {
            val packet = Day13.IntPacket(5)

            assertThat(packet).isEqualTo(Day13.IntPacket(5))
            assertThat(packet).isGreaterThanOrEqualTo(Day13.IntPacket(5))
            assertThat(packet).isGreaterThan(Day13.IntPacket(4))
            assertThat(packet).isLessThan(Day13.IntPacket(6))
        }

        @Test
        fun `Compare ListPackets`() {
            // Compare [1] and [1]
            assertThat("[1]".toPacket().compareTo("[1]".toPacket())).isEqualTo(0)
            // Compare [1,1,3,1,1] vs [1,1,5,1,1]
            assertThat("[1,1,3,1,1]".toPacket()).isLessThan("[1,1,5,1,1]".toPacket())

            // Compare [[1],[2,3,4]] vs [[1],4]
            assertThat("[[1],[2,3,4]]".toPacket()).isLessThan("[[1],4]".toPacket())

            // Compare [9] vs [[8,7,6]]
            assertThat("[9]".toPacket()).isGreaterThan("[[8,7,6]".toPacket())

            // Compare [[4,4],4,4] vs [[4,4],4,4,4]
            assertThat("[[4,4],4,4]".toPacket()).isLessThan("[[4,4],4,4,4]".toPacket())
        }

        @Test
        fun `Compere different packets`() {
            val packet = "[[2,3,4]]".toPacket()
            val other : Day13.Packet = Day13.IntPacket(4)

            assertThat(other).isGreaterThan(packet)
            assertThat(packet).isLessThan(other)
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
            assertThat(answer).isEqualTo(13)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part1(input)

            // Assert
            assertThat(answer).isEqualTo(5580)
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
            assertThat(answer).isEqualTo(140)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part2(input)

            // Assert
            assertThat(answer).isEqualTo(26200)
        }
    }
}

