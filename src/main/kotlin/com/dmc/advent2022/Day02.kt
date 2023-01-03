// --- Day 2: Rock Paper Scissors ---
package com.dmc.advent2022

class Day02 : Day<Int> {
    override val index = 2

    override fun part1(input: List<String>): Int {
        return input.sumOf { calcScore1(it.split(" ")) }
    }

    override fun part2(input: List<String>): Int {
        return input.sumOf { calcScore2(it.split(" ")) }
    }
}
enum class OutCome(val score: Int) {
    LOSE(0),
    DRAW(3),
    WIN(6);

    fun getOppositeOutcome() : OutCome {
        return when(this) {
            DRAW -> DRAW
            WIN -> LOSE
            LOSE -> WIN
        }
    }
}

enum class Shape(val score: Int, val vsRock: OutCome, val vsPaper: OutCome, val vsSciccor: OutCome) {
    ROCK(1, OutCome.DRAW, OutCome.LOSE, OutCome.WIN),
    PAPER(2, OutCome.WIN, OutCome.DRAW, OutCome.LOSE),
    SCISSORS(3, OutCome.LOSE, OutCome.WIN, OutCome.DRAW);

    fun outcome(otherShape: Shape) : OutCome {
        return when (otherShape) {
            ROCK -> this.vsRock
            PAPER -> this.vsPaper
            SCISSORS -> this.vsSciccor
        }
    }

    fun getExpectedOutcome(outCome: OutCome) : Shape {
        return when (outCome) {
            this.vsPaper -> PAPER
            this.vsRock -> ROCK
            else -> SCISSORS
        }
    }
}

fun String.toShape(): Shape {
    return when (this) {
        "A","X" -> Shape.ROCK
        "B","Y" -> Shape.PAPER
        else -> Shape.SCISSORS
    }
}

fun String.toOutCome() : OutCome {
    return when (this) {
        "X" -> OutCome.LOSE
        "Y" -> OutCome.DRAW
        else -> OutCome.WIN
    }
}


fun calcScore1(input: List<String>) : Int {
    return calcScore1(input[0].toShape(),input[1].toShape())
}

fun calcScore1(opponent: Shape, myShape: Shape) : Int {
    // Shape score
    val shapeScore = myShape.score
    // Did I win?
    val outcomeScore = myShape.outcome(opponent).score

    // Add it
    return shapeScore + outcomeScore
}


fun calcScore2(input: List<String>) : Int {
    return calcScore2(input[0].toShape(),input[1].toOutCome())
}

fun calcScore2(opponent: Shape, expectedOutCome: OutCome) : Int {
    // Determine shape (note that Shape.getExpectedOutcome is the outcome for the opponent)
    val myShape = opponent.getExpectedOutcome(expectedOutCome.getOppositeOutcome())

    // Add it
    return myShape.score + expectedOutCome.score
}

fun main() {
    val day = Day02()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 15)

    val input = readInput(day.index)
    day.part1(input).println()
    day.part2(input).println()
}
