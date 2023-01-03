// --- Day 2: Rock Paper Scissors ---
package com.dmc.advent2022

class Day02 : Day<Int> {
    override val index = 2

    override fun part1(input: List<String>): Int {
        return input.map{ calcScore1(it.split(" ")) }.sum()
    }

    override fun part2(input: List<String>): Int {
        return input.map{ calcScore2(it.split(" ")) }.sum()
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

fun getOpponentShape(input: String): Shape {
    return when (input) {
        "A" -> Shape.ROCK
        "B" -> Shape.PAPER
        else -> Shape.SCISSORS
    }
}

fun getYourShape(input: String): Shape {
    return when (input) {
        "X" -> Shape.ROCK
        "Y" -> Shape.PAPER
        else -> Shape.SCISSORS
    }
}

fun getYourOutCome(input: String): OutCome {
    return when (input) {
        "X" -> OutCome.LOSE
        "Y" -> OutCome.DRAW
        else -> OutCome.WIN
    }
}


fun calcScore1(input: List<String>) : Int {
    return calcScore1(getOpponentShape(input[0]),getYourShape(input[1]))
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
    return calcScore2(getOpponentShape(input[0]),getYourOutCome(input[1]))
}

fun calcScore2(opponent: Shape, expectedOutCome: OutCome) : Int {
    // Determine shape (note that Shape.getExpectedOutcome is the outcome for the opponent)
    val myShape = opponent.getExpectedOutcome(expectedOutCome.getOppositeOutcome())

    // Shape score
    val shapeScore = myShape.score
    // Did I win?
    val outcomeScore = myShape.outcome(opponent).score

    // Add it
    return shapeScore + outcomeScore
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
