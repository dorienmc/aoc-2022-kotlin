//--- Day 13: Distress Signal ---
package com.dmc.advent2022

class Day13 : Day<Int> {
    override val index = 13

    override fun part1(input: List<String>): Int {
        val packets = input.filter { it.isNotEmpty() }.map{ it.toPacket() }

        return packets.chunked(2).withIndex().filter {
            it.value[0] < it.value[1] // left should be smaller than right
        }.sumOf {
            it.index + 1 // first pair has index 1
        }
    }

    override fun part2(input: List<String>): Int {
        val packets = input.filter { it.isNotEmpty() }.map{ it.toPacket() }.toMutableList()

        //Add divider packets
        val divider2 = "[[2]]".toPacket()
        val divider6 = "[[6]]".toPacket()

        // Sort and find indices of dividers
        val sorted = (packets + divider2 + divider6).sorted()
        return (sorted.indexOf(divider2) + 1) * (sorted.indexOf(divider6) + 1)

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
                return ListPacket(packets).first() //need the first() otherwise it is packages twice
            }
        }
    }

    data class IntPacket(val value: Int) : Packet() {
        fun asList(): Packet = ListPacket(mutableListOf(this))

        override fun compareTo(other: Packet): Int =
            when (other) {
                is IntPacket -> this.value.compareTo(other.value)
                is ListPacket -> this.asList().compareTo(other)
            }

        override fun toString(): String = value.toString()
    }

    class ListPacket(private val packet: MutableList<Packet> = mutableListOf()) : Packet(), Iterable<Packet> {

        override fun size() : Int {
            return packet.size
        }

        override fun compareTo(other: Packet): Int {
//            println("Compare $this to $other")
            return when (other) {
                is IntPacket -> this.compareTo(other.asList())
                is ListPacket -> {
                    return this.packet.zip(other.packet).map {
                        it.first.compareTo(it.second)
                    }.firstOrNull { it != 0 } ?: this.size().compareTo(other.size())
                }
            }
        }

        override fun toString(): String {
            return packet.toString()
        }

        override fun iterator(): Iterator<Packet> {
            return packet.iterator()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ListPacket

            if (packet != other.packet) return false

            return true
        }

        override fun hashCode(): Int {
            return packet.hashCode()
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
