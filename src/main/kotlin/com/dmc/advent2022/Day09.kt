package com.dmc.advent2022

import kotlin.math.absoluteValue
import kotlin.math.sign

//--- Day 9: Rope Bridge ---
class Day09 : Day<Int> {
    override val index = 9

    override fun part1(input: List<String>): Int {
        var head = Point()
        var tail = Point()
        val tailPositions = mutableSetOf(Point())

        for (line in input) {
            val (direction, num) = line.split(" ")

            // Move head and tail
            repeat(num.toInt()) {
                head = head.move(direction)
                if (!head.touches(tail)) {
                    tail = tail.moveTowards(head)
                }
                tailPositions += tail
            }
        }

        return tailPositions.size
    }

    override fun part2(input: List<String>): Int {
        val rope = (0..9).map{ Point()}.toMutableList()
        val tailPositions = mutableListOf(rope.last())

        for (line in input) {
            val (direction, num) = line.split(" ")

            // Move head, knots and tail
            repeat(num.toInt()) {
                // Move head
                rope[0] =  rope[0].move(direction)

                // Move the rest
                rope.indices.windowed(2){ (prev, current) ->
                    val prevKnot = rope.get(prev)
                    var knot = rope.get(current)
                    if (!knot.touches(prevKnot)) {
                        knot = knot.moveTowards(prevKnot)
                    }
                    check(knot.touches(prevKnot))
                    rope[current] = knot
                }
                // Store tail position
                tailPositions += rope.last()
            }
        }
//        println(tailPositions)

        return tailPositions.toSet().size
    }
}

data class Point(val x: Int = 0, val y: Int = 0) {
    // y |
    //   |___
    //       x

    fun move(direction: String): Point =
        when(direction) {
            "U" -> copy(y = y + 1)
            "D" -> copy(y = y - 1)
            "R" -> copy(x= x + 1)
            "L" -> copy(x = x - 1)
            else -> this
        }

    fun touches(other: Point): Boolean {
        // diagonally adjacent and even overlapping both count as touching
        val diffX = (x - other.x).absoluteValue
        val diffY = (y - other.y).absoluteValue
        return diffX <= 1 && diffY <= 1
    }

    fun moveTowards(other: Point) : Point {
        return Point(x + (other.x - x).sign, y + (other.y - y).sign)
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}


fun main() {
    val day = Day09()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 13)

    val input = readInput(day.index)
    day.part1(input).println()

    val testInput2 = readInput("Day09_test2","src/test/resources")
    check(day.part2(testInput2) == 36)
    day.part2(input).println()

}
