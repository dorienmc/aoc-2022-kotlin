package com.dmc.advent2022

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, path: String = "src/main/resources") = File(path, "$name.txt")
    .readLines()

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: Int, test: Boolean = false): List<String> {
    val dayNr = day.toString().padStart(2,'0');
    if (test){
        return readInput("Day${dayNr}","src/test/resources")
    } else {
        return readInput("Day${dayNr}")
    }
}

    /**
     * Converts string to md5 hash.
     */
    fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

    /**
     * The cleaner shorthand for printing output.
     */
    fun Any?.println() = println(this)

