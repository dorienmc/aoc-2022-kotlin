package com.dmc.advent2022

/**
 * Generic 2D Point class
 *
 * @author Dorien Lorijn
 */
data class Point2D(val x: Int = 0, val y: Int = 0) {
    fun cardinalNeighbors(): Set<Point2D> =
        setOf(
            copy(x = x - 1),
            copy(x = x + 1),
            copy(y = y - 1),
            copy(y = y + 1)
        )
}
