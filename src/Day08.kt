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
class Day08 : Day<Int> {
    override val index = 8

    override fun part1(input: List<String>): Int {
        val gridSize = input[0].length
        val grid = Matrix(gridSize, gridSize)
        val markingGrid = Matrix(gridSize, gridSize)

        // Read input
        for( (i, line) in input.withIndex() ) {
            for ( (j, elem) in line.withIndex() ) {
                grid.set(i,j, elem.digitToInt())
            }
        }
        println(grid)

        // Check rows
        for( i in 0 until grid.rows) {
            // Left to right
            val rowOfTrees = grid.getRow(i)
            var lowestVisibleTree = -1
            for ((j, tree) in rowOfTrees.withIndex()) {
                if (tree > lowestVisibleTree) {
                    lowestVisibleTree = tree
                    markingGrid.set(i,j,1)
                }
            }
        }
//        println("Check left to right: \n$markingGrid")

        for( i in 0 until grid.rows) {
            // Right to left
            var lowestVisibleTree = -1
            val rowReversed = grid.getRow(i).reversed()
            for ((j, tree) in rowReversed.withIndex()) {
                val jReversed = gridSize - j - 1
                if (tree > lowestVisibleTree) {
                    lowestVisibleTree = tree
                    markingGrid.set(i,jReversed,1)
                }
            }
        }
//        println("Check right to left: \n$markingGrid")

        // Check up to down
        for( j in 0 until grid.cols) {
            var lowestVisibleTree = -1
            for (i in 0 until grid.rows) {
                val tree = grid.get(i,j)
                if (tree > lowestVisibleTree) {
                    lowestVisibleTree = tree
                    markingGrid.set(i,j,1)
                }
            }
        }
//        println("Check up to down: \n$markingGrid")

        // Check down to up
        for( j in 0 until grid.cols) {
            var lowestVisibleTree = -1
            for (i in grid.rows - 1 downTo 0) {
                val tree = grid.get(i,j)
                if (tree > lowestVisibleTree) {
                    lowestVisibleTree = tree
                    markingGrid.set(i,j,1)
                }
            }
        }
//        println("Check down to up: \n$markingGrid")

        // Print no of visible trees
        var count = 0
        for(i in 0 until grid.rows) {
            for(j in 0 until grid.cols) {
                count += markingGrid.get(i,j)
            }
        }

        return count
    }



    override fun part2(input: List<String>): Int {
        return 0;
    }
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

    fun getRow(row: Int): IntArray {
        return matrix[row]
    }

    fun getCol(col: Int): IntArray {
        return matrix.map { row -> row[col] }.toIntArray()
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
//    day.part2(input).println()
}
