//--- Day 8: Treetop Tree House ---
/*
Each tree is represented as a single digit whose value is its height, where 0 is the shortest and 9 is the tallest.

A tree is visible if all of the other trees between it and an edge of the grid are shorter than it. Only consider trees in the same row or column; that is, only look up, down, left, or right from any given tree.

All of the trees around the edge of the grid are visible - since they are already on the edge, there are no trees to block the view. In this example, that only leaves the interior nine trees to consider:

The top-left 5 is visible from the left and top. (It isn't visible from the right or bottom since other trees of height 5 are in the way.)
The top-middle 5 is visible from the top and right.
The top-right 1 is not visible from any direction; for it to be visible, there would need to only be trees of height 0 between it and an edge.
The left-middle 5 is visible, but only from the right.
The center 3 is not visible from any direction; for it to be visible, there would need to be only trees of at most height 2 between it and an edge.
The right-middle 3 is visible from the right.
In the bottom row, the middle 5 is visible, but the 3 and 4 are not.
With 16 trees visible on the edge and another 5 visible in the interior, a total of 21 trees are visible in this arrangement.

30373
25512 -> 1 not visible
65332 -> center 3 is not visible
33549 -> 3 and 4 are not visible
35390

Consider your map; how many trees are visible from outside the grid?
 */
package com.dmc.advent2022

class Day08 : Day<Int> {
    override val index = 8

    override fun part1(input: List<String>): Int {
        val grid = input.toMatrix()

        return (0 until grid.rows).sumOf { i ->
            (0 until grid.cols).count { j ->
                grid.isVisible(i,j)
            }
        }
    }

    override fun part2(input: List<String>): Int {
        println("Part 2")
        val grid = input.toMatrix()

        // Measure viewing distance
        return (0 until grid.rows).maxOf { r -> (0 until grid.cols).maxOf{ c -> grid.scenicScore(r,c) } }
    }

    fun scenicScore(i: Int, j: Int, grid: Matrix) : Int {
        if (i == 0 || j == 0 || i == grid.rows - 1 || j == grid.cols - 1) return 0
        return grid.lookUp(i,j) * grid.lookDown(i,j) * grid.lookLeft(i,j) * grid.lookRight(i,j)
    }

}

fun List<String>.toMatrix() : Matrix {
    val gridSize = this[0].length
    val grid = Matrix(gridSize, gridSize)
    for( (i, line) in this.withIndex() ) {
        for ( (j, elem) in line.withIndex() ) {
            grid.set(i,j, elem.digitToInt())
        }
    }
    return grid
}

fun Matrix.viewFrom(i: Int, j: Int): List<List<Int>> {
    //Return all trees that are in the row/column as this tree
    //Note that some might not be visible
    return listOf(
        (j-1 downTo 0).map{ c -> this.get(i,c)}, // left
        (i-1 downTo  0).map{ r -> this.get(r,j) }, // up
        (j+1 until this.cols).map{ c -> this.get(i,c) }, //right
        (i+1 until this.rows).map{ r -> this.get(r,j) }, //down
    )
}

fun Matrix.isVisible(i: Int, j: Int) : Boolean {
    return viewFrom(i,j).any { direction ->
        direction.all{ tree -> tree < this.get(i,j) }
    }
}

fun Matrix.scenicScore(i: Int, j: Int): Int {
    return viewFrom(i,j).map { direction ->
        direction.takeUntil { it >= this.get(i,j) }.count()
    }.product()
}

fun Matrix.look(iRange: IntProgression, jRange: IntProgression, tree: Int) : Int {
    // Return number of trees that can be seen from here (and are shorter that this tree)
    var count = 0
    for (i in iRange) {
        for (j in jRange) {
            count++
            if (this.get(i,j) >= tree) {
                return count
            }
        }
    }
    return count
}

fun Matrix.lookDown(i: Int, j: Int) : Int {
    return this.look(i+1 until this.rows, j..j, this.get(i,j))
}

fun Matrix.lookUp(i: Int, j: Int) : Int {
    return this.look(i-1 downTo 0, j..j, this.get(i,j))
}

fun Matrix.lookLeft(i: Int, j: Int): Int {
    return this.look(i..i, j-1 downTo 0, this.get(i,j))
}

fun Matrix.lookRight(i: Int, j: Int): Int {
    return this.look(i..i, j+1 until this.cols, this.get(i,j))
}


class Matrix(var rows: Int, var cols: Int) {
    val matrix = Array(rows) { IntArray(cols) {0} }

    override fun toString(): String {
        return matrix.joinToString(separator = "\n") { it.contentToString() }
    }

    fun set(row: Int, col: Int, value: Int) {
        matrix[row][col] = value
    }

    fun get(row: Int, col: Int): Int {
        return matrix[row][col]
    }

    fun getRow(row: Int, jRange: IntProgression = 0 until cols): IntArray {
        return jRange.map{ j -> get(row, j) }.toIntArray()
    }

    fun getCol(col: Int, iRange: IntProgression = 0 until rows): IntArray {
        return iRange.map{ i -> get(i, col) }.toIntArray()
    }
}

fun main() {
    val day = Day08()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 21)
//
    val input = readInput(day.index)
    day.part1(input).println()
//
    check(day.part2(testInput) == 8)
    day.part2(input).println()

}
