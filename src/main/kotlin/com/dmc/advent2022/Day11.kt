package com.dmc.advent2022

//--- Day 11: Monkey in the Middle ---
class Day11 : Day<Long> {
    override val index = 11

    override fun part1(input: List<String>): Long {
        val monkeys: List<Monkey> = input.chunked(7).map { Monkey.of(it) }

        repeat(20) {
            monkeys.play{ it / 3 }
        }
        monkeys.forEach{ m -> println("$m inspected items ${m.numInspectedItems} times")}
        return monkeys.business()

    }

    override fun part2(input: List<String>): Long {
        val monkeys: List<Monkey> = input.chunked(7).map { Monkey.of(it) }
        val monkeyMod = monkeys.map { it.test }.product()

        repeat(10_000) {
            monkeys.play{ it % monkeyMod }
        }
        monkeys.forEach{ m -> println("$m inspected items ${m.numInspectedItems} times")}
        return monkeys.business()
    }
}

fun List<Monkey>.business() : Long =
    this
    .sortedByDescending { it.numInspectedItems }
    .take(2)
    .map { it.numInspectedItems }
    .reduce{ a,b -> a * b }

fun List<Monkey>.play(changeToWorryLevel: (Long) -> Long) {
    this.forEach { monkey ->
        val thrownItems = monkey.turn(changeToWorryLevel)

        for(entry in thrownItems) {
            val item = entry.first
            val otherMonkey : Monkey = this[entry.second]
            otherMonkey.catchItem(item)
        }
    }
}

class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: Long,
    val trueMonkey: Int,
    val falseMonkey: Int
    ) {

    var numInspectedItems: Long = 0L

    companion object {
        fun of(input: List<String>): Monkey {
            val id = input[0].substringAfter(" ").substringBefore(":").toInt()
            val items = input[1].substringAfter(":").split(",").map{ it.trim().toLong() }.toMutableList()
            val operationFunction = input[2].substringAfter("new = ")
            val operationVal = input[2].substringAfterLast(" ")
            val operation: (Long) -> Long = when {
                operationFunction == "old * old" -> ({it * it})
                "+" in operationFunction -> ({it + operationVal.toLong()})
                operationFunction.contains("*") -> ({it * operationVal.toLong()})
                else -> ({it})
            }
            val test = input[3].substringAfter("by ").toLong()
            val trueMonkey = input[4].substringAfterLast(" ").toInt()
            val falseMonkey = input[5].substringAfterLast(" ").toInt()
            return Monkey(id, items, operation, test, trueMonkey, falseMonkey)
        }
    }

    fun turn(changeToWorryLevel: (Long) -> Long): List<Pair<Long,Int>> {
        //On a single monkey's turn,
        //it inspects and throws all of the items it is holding one at a time and in the order listed.
        val thrownItems = mutableListOf<Pair<Long,Int>>()
        for(item in items) {
            //inspect
            val worry = changeToWorryLevel(operation(item))
            val targetMonkey = if (worry % test == 0L) trueMonkey else falseMonkey
            thrownItems.add(worry to targetMonkey)
        }
        numInspectedItems += items.size
        items.clear()
        return thrownItems
    }

    fun catchItem(item: Long) {
        items.add(item)
    }

    override fun toString(): String {
        return "monkey $id: ${items.joinToString(",")}"
    }
}


fun main() {
    val day = Day11()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 10605L)

    val input = readInput(day.index)
    day.part1(input).println()
    
    day.part2(testInput).println()
    day.part2(input).println()
}
