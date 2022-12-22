plugins {
    kotlin("jvm") version "1.7.21"
}

repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }

    task("generateNextDay") {
        doLast {
            println("test")
            val prevDayNum = fileTree("$projectDir/src").matching {
                include("Day*.kt").exclude("Day.kt")
            }.maxOf {
                val (prevDayNum) = Regex("Day(\\d\\d)").find(it.name)!!.destructured
                prevDayNum.toInt()
            }
            val newDayNum = String.format("%02d", prevDayNum + 1)
            println("Generating files for day $newDayNum")
            File("$projectDir/src/resources","Day${newDayNum}_test.txt").writeText("")
            File("$projectDir/src/resources","Day${newDayNum}.txt").writeText("")
            File("$projectDir/src", "Day$newDayNum.kt").writeText(
                """
//--- Day $newDayNum: ??? ---
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
"""
            )
        }
    }
}
