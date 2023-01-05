package com.dmc.advent2022

import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, path: String = "src/main/resources") = File(path, "$name.txt")
    .readLines()

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: Int, test: Boolean = false): List<String> {
    val dayNr = day.toString().padStart(2,'0')
    if (test){
        return readInput("Day${dayNr}","src/test/resources")
    } else {
        return readInput("Day${dayNr}")
    }
}


/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Take element until a condition is met (similar to takeWhile)
 */
inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (predicate(item)) {
            break
        }
    }
    return list
}

/**
 * Return the product of a list of ints
 */
fun Iterable<Int>.product(): Int = reduce{ a,b -> a * b }
