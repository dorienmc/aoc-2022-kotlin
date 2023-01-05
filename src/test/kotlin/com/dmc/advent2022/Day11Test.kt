/**
 * @author Dorien Lorijn
 */

package com.dmc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 11")
internal class Day11Test {
    val day: Day11 = Day11()
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
        fun `parse input`() {
            val monkeys = readInput(day.index, true).chunked(7).map{ Monkey.of(it) }

            assertThat(monkeys.size).isEqualTo(4)
            assertThat(monkeys[2].id).isEqualTo(2)
            assertThat(monkeys[1].items).containsExactly(54,65,75,74)
            assertThat(monkeys[0].test).isEqualTo(23)
            assertThat(monkeys[0].trueMonkey).isEqualTo(2)
            assertThat(monkeys[0].falseMonkey).isEqualTo(3)
            assertThat(monkeys[3].operation.invoke(42)).isEqualTo(45)
        }

        @Test
        fun `monkey inspection`() {
            val monkeys = readInput(day.index, true).chunked(7).map{ Monkey.of(it) }

            var items = monkeys[0].turn{ it /3 }
            assertThat(items.size).isEqualTo(monkeys[0].numInspectedItems)
            assertThat(items).containsExactly(
                (500L to 3),
                (620L to 3)
            )

            items = monkeys[1].turn{ it /3 }
            assertThat(items.size).isEqualTo(monkeys[1].numInspectedItems)
            assertThat(items).containsExactly(
                (20L to 0),
                (23L to 0),
                (27L to 0),
                (26L to 0)
            )
        }

        @Test
        fun `monkey play`() {
            val monkeys = readInput(day.index, true).chunked(7).map{ Monkey.of(it) }

            monkeys.play{ it/3 }
            assertThat(monkeys[0].items).containsExactly(20L,23L,27L,26L)
            assertThat(monkeys[1].items).containsExactly(2080L,25L,167L, 207L, 401L, 1046L)
            assertThat(monkeys[2].items).isEmpty()
            assertThat(monkeys[3].items).isEmpty()
        }

        @Test
        fun `monkey business`() {
            val monkeys = readInput(day.index, true).chunked(7).map{ Monkey.of(it) }

            assertThat(monkeys.business()).isEqualTo(0L)
            monkeys.play{ it/3 }
            assertThat(monkeys.business()).isEqualTo(20L)
        }

        @Test
        fun `more monkey business`() {
            val monkeys = readInput(day.index, true).chunked(7).map{ Monkey.of(it) }
            val monkeyMod = monkeys.map { it.test }.product()

            assertThat(monkeys.business()).isEqualTo(0L)
            monkeys.play{ it/3 % monkeyMod }
            assertThat(monkeys.business()).isEqualTo(20L)
        }

        @Test
        fun `a bit of monkey play`() {
            val monkeys = readInput(day.index, true).chunked(7).map{ Monkey.of(it) }
            val monkeyMod = monkeys.map { it.test }.product()

            repeat(20) {
                monkeys.play { it % monkeyMod }
            }
            assertThat(monkeys[0].numInspectedItems).isEqualTo(99)
            assertThat(monkeys[1].numInspectedItems).isEqualTo(97)
            assertThat(monkeys[2].numInspectedItems).isEqualTo(8)
            assertThat(monkeys[3].numInspectedItems).isEqualTo(103)
        }

        @Test
        fun `lots of monkey play`() {
            val monkeys = readInput(day.index, true).chunked(7).map{ Monkey.of(it) }
            val monkeyMod = monkeys.map { it.test }.product()

            repeat(1000) {
                monkeys.play { it % monkeyMod }
            }
            assertThat(monkeys[0].numInspectedItems).isEqualTo(5204L)
            assertThat(monkeys[1].numInspectedItems).isEqualTo(4792L)
            assertThat(monkeys[2].numInspectedItems).isEqualTo(199L)
            assertThat(monkeys[3].numInspectedItems).isEqualTo(5192L)
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
            assertThat(answer).isEqualTo(10_605L)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part1(input)

            // Assert
            assertThat(answer).isEqualTo(119_715L)
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
            assertThat(answer).isEqualTo(2713310158)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part2(input)

            // Assert
            assertThat(answer).isEqualTo(18085004878)
        }
    }
}
