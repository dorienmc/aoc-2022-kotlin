import java.lang.Math.abs

//--- Day 9: Rope Bridge ---
class Day09 : Day<Int> {
    override val index = 9

    override fun part1(input: List<String>): Int {
        var posH = Position(0,0)
        var posT = Position(0,0)
        val tailPositions = mutableListOf(posT)

        for (line in input) {
            val direction = line.split(" ")[0]
            val num = line.split(" ")[1].toInt()
//            println("== $direction $num ==")

            // Move head and tail
            for(i in 1..num) {
                posH = posH.go(direction)
//                println("H $posH, T $posT")
                if (!posH.isTouching(posT)) {
                    posT = moveCloser(posH, posT)

                    // Store tail position
                    tailPositions.add(posT)
                }
//                println("H $posH, T $posT")
                check(posH.isTouching(posT))
            }
        }
//        println(tailPositions)

        return tailPositions.toSet().size
    }

    private fun moveCloser(head : Position, tail: Position): Position {
        val diffX = head.x - tail.x
        val diffY = head.y - tail.y

        val moveX = when {
            diffX <= -1 -> -1
            diffX >= 1 -> 1
            else -> 0
        }
        val moveY = when {
            diffY <= -1 -> -1
            diffY >= 1 -> 1
            else -> 0
        }
//        println("$moveX $moveY")

        return tail.plus(moveX, moveY)
    }

    override fun part2(input: List<String>): Int {
        val pos = (0..9).map{ Position(0,0)}.toMutableList()
        val tailPositions = mutableListOf(pos.last())

        for (line in input) {
            val direction = line.split(" ")[0]
            val num = line.split(" ")[1].toInt()

            // Move head, knots and tail
            for(i in 1..num) {
                val posH = pos.get(0)
                pos[0] = posH.go(direction)

                for (j in 1..9) {
                    val prevKnot = pos.get(j - 1)
                    var knot = pos.get(j)

                    if (!knot.isTouching(prevKnot)) {
                        knot = moveCloser(prevKnot, knot)

                        // Store tail position
                        if (j == 9) {
                            tailPositions.add(knot)
                        }
                    }
                    check(knot.isTouching(prevKnot))
                    pos[j] = knot
                }
            }
        }
        println(tailPositions)

        return tailPositions.toSet().size
    }
}

data class Position(var x: Int, var y: Int) {
    // y |
    //   |___
    //       x

    fun go(direction: String): Position {
        return when(direction) {
            "U" -> this.plus(0,1)
            "D" -> this.plus(0,-1)
            "R" -> this.plus(1,0)
            "L" -> this.plus(- 1, 0)
            else -> return this
        }
    }

    fun plus(i: Int, j:Int) : Position {
        return Position(this.x + i, this.y + j)
    }

    fun isTouching(other: Position): Boolean {
        // diagonally adjacent and even overlapping both count as touching
        val diffX = abs(this.x - other.x)
        val diffY = abs(this.y - other.y)
        return diffX <= 1 && diffY <= 1
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Position) {
            return this.x == other.x && this.y == other.y
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}


fun main() {
    val day = Day09()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 13)

    val input = readInput(day.index)
    day.part1(input).println()

    val testInput2 = readInput("Day09_test2")
    check(day.part2(testInput2) == 36)
    day.part2(input).println()

}
