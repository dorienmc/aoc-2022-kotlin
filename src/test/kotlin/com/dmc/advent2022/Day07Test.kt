/**
 * @author Dorien Lorijn
 */

package com.dmc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 7")
internal class Day07Test {
    val day: Day07 = Day07()
    var testInput: List<String> = listOf()
    var input: List<String> = listOf()

    @BeforeEach
    fun setUp() {
        testInput = readInput(day.index, true)
        input = readInput(day.index)
    }

    @Nested
    @DisplayName("Test sizes")
    inner class Example {
        val root = Directory("/")
        val dirA = Directory("a")
        val dirE = Directory("e")
        val fileI = MyFile("i", 584)
        val fileF = MyFile("f", 29116)
        val fileG = MyFile("g", 2557)
        val fileH = MyFile("h", 62596)
        val fileB = MyFile("b",14848514)
        val fileC = MyFile("c", 8504156)
        val dirD = Directory("d")
        val fileJ = MyFile("j", 4060174)
        val fileDlog = MyFile("d.log", 8033020)
        val fileDext = MyFile("d.ext", 5626152)
        val fileK = MyFile("k",7214296)

        @BeforeEach
        fun setup() {
            dirE.addFile(fileI)
            dirA.addSubdir(dirE)
            dirA.addFile(fileF)
            dirA.addFile(fileG)
            dirA.addFile(fileH)
            dirD.addFile(fileJ)
            dirD.addFile(fileDlog)
            dirD.addFile(fileDext)
            dirD.addFile(fileK)
            root.addSubdir(dirA)
            root.addFile(fileB)
            root.addFile(fileC)
            root.addSubdir(dirD)
        }

        @Test
        fun `Directory E`() {
            assertThat(dirE.getSize()).isEqualTo(584)
        }

        @Test
        fun `Directory A`() {
            assertThat(dirA.getSize()).isEqualTo(94853)
        }

        @Test
        fun `Directory D`() {
            assertThat(dirD.getSize()).isEqualTo(24933642)
        }

        @Test
        fun `Root directory`() {
            assertThat(root.getSize()).isEqualTo(48381165)
        }

        @Test
        fun `parseInput should work`() {
            val dirs = day.parseInput(testInput)
            val expectedValues = mapOf("/" to 48381165, "a" to 94853, "d" to 24933642, "e" to 584)

            assertThat(dirs.size).isEqualTo(4)
            for (dir in dirs) {
                assertThat(dir.name).isIn(expectedValues.keys)
                assertThat(dir.getSize()).isEqualTo(expectedValues.get(dir.name))
            }
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
            assertThat(answer).isEqualTo(95437)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part1(input)

            // Assert
            assertThat(answer).isEqualTo(1555642)
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
            assertThat(answer).isEqualTo(24933642)
        }

        @Test
        fun `Actual answer`() {
            // Act
            val answer = day.part2(input)

            // Assert
            assertThat(answer).isEqualTo(5974547)
        }
    }
}
