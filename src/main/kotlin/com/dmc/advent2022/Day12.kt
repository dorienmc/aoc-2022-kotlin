//--- Day 12: Hill Climbing Algorithm ---
package com.dmc.advent2022

import java.util.*

class Day12 : Day<Int> {
    override val index = 12
    
    override fun part1(input: List<String>): Int {
        val heightmap = parseInput(input)

        return heightmap.shortestPath(heightmap.start, heightmap.end)!!
    }
    override fun part2(input: List<String>): Int {
        val heightmap = parseInput(input)
        val startPoints = heightmap.elevations.filter { it.value == 0 }.map { it.key }

        return startPoints.map{ heightmap.shortestPath(it, heightmap.end) }.filterNotNull().min()
    }

    class Heightmap(val elevations : Map<Point2D, Int>, val start: Point2D, val end: Point2D) {

        fun shortestPath(begin: Point2D, end: Point2D) : Int? {
            val queue = PriorityQueue<Path>().apply { Path(begin, 0) }
            val explored = mutableSetOf<Point2D>()

            // BFS
            while (queue.isNotEmpty()) {
                val nextPoint = queue.poll()

                if(nextPoint.point !in explored) {
                    explored.add(nextPoint.point)
                    val neighbours = nextPoint.point.friendlyNeighbours()
                    val nextCost = nextPoint.cost + 1
                    // If neighbour is goal then we are almost there
                    if (neighbours.any { it == end }) return nextCost
                    // Otherwise, keep on looking
                    queue.addAll(neighbours.map { Path(it, nextCost) })
                }
            }
            // No path found
            return null
//            throw IllegalStateException("No valid path from $start to $end")
        }

        fun canMove(from: Point2D, to: Point2D) : Boolean {
            return (elevations.getValue(to) - elevations.getValue(from)) <= 1
        }

        private fun Point2D.friendlyNeighbours() : List<Point2D> {
            return this.cardinalNeighbors()
                .filter { it in elevations }
                .filter { canMove(this, it)  }
        }

        fun getFriendlyNeighbours(point: Point2D) : List<Point2D> {
            return point.friendlyNeighbours()
        }
    }

    class Path(val point: Point2D, val cost: Int) : Comparable<Path> {
        override fun compareTo(other: Path): Int =
            this.cost.compareTo(other.cost)
    }


    fun parseInput(input: List<String>) : Heightmap {
        var start: Point2D? = null
        var end: Point2D? = null
        val elevations = input.flatMapIndexed { x, line ->
            line.mapIndexed{ y, elem ->
                val here = Point2D(x, y)
                here to when (elem) {
                    'S' -> 0.also { start = here }
                    'E' -> 25.also { end = here }
                    else -> elem - 'a'
                }
            }
        }.toMap()
        return Heightmap(elevations, start!!, end!!)
    }
}


fun main() {
    val day = Day12()
    
    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 0)
    
    val input = readInput(day.index)
    day.part1(input).println()
    
    day.part2(testInput)
    day.part2(input).println()
}
