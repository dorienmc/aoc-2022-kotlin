import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    implementation("org.assertj:assertj-core:3.23.1")

    testImplementation("org.assertj:assertj-core:3.23.1") {
        because("Nicked this from tginsberg/advent-2022-kotlin")
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    test {
        useJUnitPlatform()
    }

    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    task("generateNextDay") {
        doLast {
            println("test")
            val prevDayNum = fileTree("$projectDir/src/main/kotlin/com/dmc/advent2022").matching {
                include("Day*.kt").exclude("Day.kt")
            }.maxOf {
                val (prevDayNum) = Regex("Day(\\d\\d)").find(it.name)!!.destructured
                prevDayNum.toInt()
            }
            val newDayNum = String.format("%02d", prevDayNum + 1)
            println("Generating files for day $newDayNum")
            File("$projectDir/src/test/resources","Day${newDayNum}.txt").writeText("")
            File("$projectDir/src/main/resources","Day${newDayNum}.txt").writeText("")
            File("$projectDir/src/main/kotlin/com/dmc/advent2022", "Day$newDayNum.kt").writeText(
                """
                    //--- Day $newDayNum: ??? ---
                    package com.dmc.advent2022
                    
                    class Day$newDayNum : Day<Int> {
                        override val index = $newDayNum
                        
                        override fun part1(input: List<String>): Int {
                            return 0
                        }
                        override fun part2(input: List<String>): Int {
                            return 0
                        }
                    }
                    
                    fun main() {
                        val day = Day$newDayNum()
                        
                        // test if implementation meets criteria from the description, like:
                        val testInput = readInput(day.index, true)
                        check(day.part1(testInput) == 0)
                        
                        val input = readInput(day.index)
                        day.part1(input).println()
                        
                        day.part2(testInput)
                        day.part2(input).println()
                    }
                """.trimIndent()
            )
            File("$projectDir/src/test/kotlin/com/dmc/advent2022", "Day${newDayNum}Test.kt").writeText(
                """
                    /**
                     * Unit tests for Day $newDayNum
                     * @author Dorien Lorijn
                     */

                    package com.dmc.advent2022

                    import org.assertj.core.api.Assertions.assertThat
                    import org.junit.jupiter.api.BeforeEach
                    import org.junit.jupiter.api.DisplayName
                    import org.junit.jupiter.api.Nested
                    import org.junit.jupiter.api.Test


                    @DisplayName("Day $newDayNum")
                    internal class Day${newDayNum}Test {
                        val day: Day$newDayNum = Day$newDayNum()
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
                            fun `parseInput`(){
                    
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
                                assertThat(answer).isEqualTo(0)
                            }

                            @Test
                            fun `Actual answer`() {
                                // Act
                                val answer = day.part1(input)

                                // Assert
                                assertThat(answer).isEqualTo(0)
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
                                assertThat(answer).isEqualTo(0)
                            }

                            @Test
                            fun `Actual answer`() {
                                // Act
                                val answer = day.part2(input)

                                // Assert
                                assertThat(answer).isEqualTo(0)
                            }
                        }
                    }


                """.trimIndent()
            )
        }
    }
}
