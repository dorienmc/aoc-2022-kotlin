//--- Day 13: Distress Signal ---
package com.dmc.advent2022

class Day13 : Day<Int> {
    override val index = 13

    override fun part1(input: List<String>): Int {
        val packets = input.filter { it.isNotEmpty() }.map{ it.toPacket() }

        return packets.chunked(2).withIndex().filter {
            it.value[0] < it.value[1] // left should be smaller than right
        }.sumOf({
            it.index + 1 // first pair has index 1
        })
    }

    override fun part2(input: List<String>): Int {
        TODO("Not yet implemented")
    }

    sealed class Packet : Comparable<Packet> {
        open fun size() : Int { return 1 }

        companion object {
            fun of(input: Iterator<String>) : Packet {
                val packets = mutableListOf<Packet>()

                while(input.hasNext()) {
                    when (val elem = input.next()) {
                        "[" -> packets.add(of(input))
                        "]" -> return ListPacket(packets)
                        ",", "" -> {/*skip*/}
                        else -> {
                            if (elem.isNumeric()) {
                                packets += IntPacket(elem.toInt())
                            }
                        }
                    }
                }
                return ListPacket(packets).get(0) //need the get(0) otherwise it is packages twice
            }
        }
    }

    data class IntPacket(val value: Int) : Packet() {
        override fun compareTo(other: Packet): Int {
//            println("Compare $this to $other")
            return if (other is IntPacket) {
                this.value.compareTo(other.value)
            } else {
                ListPacket(mutableListOf(this)).compareTo(other)
            }
        }

        override fun toString(): String {
            return value.toString()
        }
    }

    class ListPacket(private val packet: MutableList<Packet> = mutableListOf()) : Packet(), Iterable<Packet> {

        override fun size() : Int {
            return packet.size
        }

        override fun compareTo(other: Packet): Int {
//            println("Compare $this to $other")
            return when (other) {
                is IntPacket -> this.compareTo(ListPacket(mutableListOf(other)))
                is ListPacket -> {
                    for( i in 0 until this.size()) {
                        // If the right list runs out of items first, the inputs are not in the right order.
                        if (i >= other.size()) return 1

                        // Compare elements
                        val comparison = this.get(i).compareTo(other.get(i))
                        if (comparison != 0) return comparison
                        // Otherwise continue
                    }
                    if (this.size() < other.size()) return -1
                    if (this.size() == other.size()) return 0

                    return -1
                }
            }
        }

        override fun toString(): String {
            return packet.toString()
        }

        fun get(index: Int): Packet {
            return packet.get(index)
        }

        override fun iterator(): Iterator<Packet> {
            return packet.iterator()
        }
    }


}


fun String.toPacket() : Day13.Packet {
    return Day13.Packet.of(this.split("""((?<=[\[\],])|(?=[\[\],]))""".toRegex()).iterator())
}

fun String.isNumeric(): Boolean {
    return this.all { char -> char.isDigit() }
}

fun main() {
    val day = Day13()
    
    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)
    check(day.part1(testInput) == 13)
    
    val input = readInput(day.index)
    day.part1(input).println()
    
    day.part2(testInput)
    day.part2(input).println()
}
