
//--- Day 11: Monkey in the Middle ---
class Day11 {
    val index = 11

    fun createTestMonkeys(): List<Monkey> {
        val m0 = Monkey(0, mutableListOf(79,98), { a -> a * 19 }, { a -> if (a % 23 ==0) 2 else 3 })
        val m1 = Monkey(1, mutableListOf(54,65,75,74), { a -> a + 6 }, { a -> if (a % 19 == 0) 2 else 0})
        val m2 = Monkey(2, mutableListOf(79, 60, 97), { a -> a * a }, { a -> if (a % 13 == 0) 1 else 3})
        val m3 = Monkey(3, mutableListOf(74), { a -> a + 3 }, { a -> if (a % 17 == 0) 0 else 1})
        return listOf(m0, m1, m2, m3)
    }

    fun createMonkeys(): List<Monkey> {
        return listOf(
            Monkey(0, mutableListOf(89, 73, 66, 57, 64, 80), { a -> a * 3}, { a -> if (a % 13 == 0) 6 else 2}),
            Monkey(1, mutableListOf(83, 78, 81, 55, 81, 59, 69), { a -> a + 1}, { a -> if (a % 3 == 0) 7 else 4}),
            Monkey(2, mutableListOf(76, 91, 58, 85), { a -> a * 13}, { a -> if (a % 7 == 0) 1 else 4}),
            Monkey(3, mutableListOf(71, 72, 74, 76, 68), { a -> a * a }, { a -> if (a % 2 == 0) 6 else 0}),
            Monkey(4, mutableListOf(98, 85, 84), { a -> a + 7}, { a -> if (a % 19 == 0) 5 else 7}),
            Monkey(5, mutableListOf(78), { a -> a + 8}, { a -> if (a % 5 == 0) 3 else 0}),
            Monkey(6, mutableListOf(86, 70, 60, 88, 88, 78, 74, 83), { a -> a + 4}, { a -> if (a % 11 == 0) 1 else 2}),
            Monkey(7, mutableListOf(81, 58), { a -> a + 5}, { a -> if (a % 17 == 0) 3 else 5})
        )
    }

    fun playRound(monkeys : List<Monkey>, worryDivision: Int = 3){
        for (monkey in monkeys) {
//        println("-----\nTurn of $monkey")
            val thrownItems = monkey.turn(worryDivision)

            for(entry in thrownItems) {
                val item = entry.first
                val otherMonkey = monkeys[entry.second]
                otherMonkey.catchItem(item)
//            println("Monkey ${entry.second} caught item $item")
            }
        }
    }

    private fun getMonkeyBusiness(monkeys: List<Monkey>) : Long {
        return monkeys
            .sortedByDescending { it.numInspectedItems }
            .take(2)
            .map { it.numInspectedItems.toLong() }
            .reduce{ a,b -> a * b }
    }

    fun part1(allMonkeys: List<Monkey>): Long {
        for(r in 1 .. 20) {
//            println("---\nRound $r")
            playRound(allMonkeys)
//            allMonkeys.forEach{ m -> println("$m: ${m.items} ")}
        }

        allMonkeys.forEach{ m -> println("$m inspected items ${m.numInspectedItems} times")}
        return getMonkeyBusiness(allMonkeys)
    }

    fun part2(allMonkeys: List<Monkey>, numRounds : Int = 10000): Long {
        for(r in 1 .. numRounds) {
//            println("---\nRound $r")
            playRound(allMonkeys, 1)
//            allMonkeys.forEach{ m -> println("$m: ${m.items} ")}
        }

        allMonkeys.forEach{ m -> println("$m inspected items ${m.numInspectedItems} times")}
        return getMonkeyBusiness(allMonkeys)
    }
}

class Monkey(val id: Int, val items: MutableList<Int>, val operation: (Int) -> Int, val test: (Int) -> Int) {
    var numInspectedItems = 0

    private fun inspect(): Int {
        numInspectedItems++
        // Monkey takes left most item and inspects it
        val item = items.first()
        items.removeAt(0)
        // Update worry level using 'operation'
        return operation.invoke(item)
    }

    fun turn(worryDivision : Int = 3): List<Pair<Int,Int>> {
        //On a single monkey's turn,
        //it inspects and throws all of the items it is holding one at a time and in the order listed.
        val thrownItems = mutableListOf<Pair<Int,Int>>()
        while (items.isNotEmpty()) {
            //inspect
            var worryLevel = inspect()
//            println("Inspect: $worryLevel")
            //monkey gets bored
            if (worryDivision > 1) {
                worryLevel /= worryDivision
//            println("Monkey bored: $worryLevel")
            }
            //throw
            val nextMonkey = test.invoke(worryLevel)
//            println("Throw to $nextMonkey")
            thrownItems.add(worryLevel to nextMonkey)
        }
        return thrownItems
    }

    fun catchItem(item: Int) {
        items.add(item)
    }

    override fun toString(): String {
        return "monkey $id"
    }

    /*
    Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3
     */
}


fun main() {
    val day = Day11()

    // test if implementation meets criteria from the description, like:
    val testInput = day.createTestMonkeys()//readInput(day.index, true)
    check(day.part1(testInput) == 10605L)

    val input = day.createMonkeys()//readInput(day.index)
    day.part1(input).println()
    
    day.part2(testInput, 20).println()
//    day.part2(input).println()
}
