package com.dmc.advent2022

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

// --- Day 7: No Space Left On Device ---

class Day07 : Day<Int> {
    override val index = 7

    fun parseInput(input: List<String>) : List<Directory> {
        val root = Directory("/")
        val dirs = mutableListOf(root)
        var currentNode: Directory = root

        // Determine file structure
        for(line in input) {
            when {
                line == "$ ls" -> {/*do nothing*/}
                line.startsWith("$ cd") -> { // cd - change currentNode
                    val (_,_,arg) = line.split(" ")
                    currentNode = goTo(currentNode, arg)
                }
                line.startsWith("dir") -> dirs.add(currentNode.addSubdir(line))
                else -> currentNode.addFile(line)
            }
        }
        return dirs.toList()
    }

    override fun part1(input: List<String>): Int {
        val allDirs = parseInput(input)
//        val root = allDirs.first()
//        println(root)

        // Get directories of at most 100000
        val boundary = 100000
        allDirs.filter{ it.getSize() <= boundary }.forEach{ println("${it.name} ${it.getSize()}")}
        return allDirs.map{ it.getSize()}.filter{ it <= boundary }.sum()
    }

    private fun goTo(currentNode: Directory, arg: String) : Directory {
        return when(arg) {
            ".." -> currentNode.parent!! //go up
            "/" -> currentNode //do nothing
            else -> currentNode.getSubdir(arg)!!
        }
    }

    override fun part2(input: List<String>): Int {
        val requiredFreeSpace = 30000000
        val totalSpace = 70000000

        val allDirs = parseInput(input)
        val root = allDirs.first()

        // Get smallest directory that when deleted ensures; unusedSpace <= requiredFreeSpace
        val unusedSpace = totalSpace - root.getSize()
        val boundary = requiredFreeSpace - unusedSpace
        var currentSmallest = root.getSize()
        for(dir in allDirs) {
            val current = dir.getSize()
            if (current in boundary..currentSmallest) {
                currentSmallest = current
            }
        }

        return currentSmallest
    }
}

data class MyFile(var name: String, var fileSize: Int) {
    override fun toString(): String {
        return "- $name (file, size=$fileSize)"
    }
}

class Directory(var name: String, val parent: Directory? = null) {
    var children: MutableList<Directory> = mutableListOf()
    var files: MutableList<MyFile> = mutableListOf()

    fun addFile(line: String) {
        val (nodesize, name) = line.split(" ")
        addFile(MyFile(name, nodesize.toInt()))
    }

    fun addFile(file: MyFile) {
        files.add(file)
    }

    fun addSubdir(line: String) : Directory {
        val name = line.substringAfter(" ")
        val child = Directory(name, this)
        return addSubdir(child)
    }

    fun addSubdir(dir: Directory) : Directory {
        children.add(dir)
        return dir
    }

    fun getSubdir(name: String) : Directory? {
        return children.find { it.name == name }
    }

    fun getSize(): Int {
        return children.sumOf { it.getSize() } + files.sumOf { it.fileSize }
    }

    override fun toString(): String {
        var result = "- $name (dir)\n"
        result += children.joinToString("\n") { child -> child.toString().prependIndent(" ") }
        result += files.joinToString("\n") { child -> child.toString().prependIndent(" ") }
        return result
    }
}

@OptIn(ExperimentalTime::class)
fun main() {
    val day = Day07()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day.index, true)

    var dur = measureTime { check(day.part1(testInput) == 95437) }
    println("Took ${dur.inWholeMicroseconds}")

    val input = readInput(day.index)
    dur = measureTime { day.part1(input).println() }
    println("Took ${dur.inWholeMicroseconds}")


    day.part2(input).println()
}
