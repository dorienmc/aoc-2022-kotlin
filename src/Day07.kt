import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

// --- Day 7: No Space Left On Device ---

class Day07 : Day<Int> {
    override val index = 7
    var allDirs : MutableList<Node> = mutableListOf()

    override fun part1(input: List<String>): Int {
        val root = Directory("/")
        allDirs = mutableListOf(root)
        var currentNode: Node = root
        var output: MutableList<String> = mutableListOf()

        // Determine file structure
        for(line in input) {
            if (line.startsWith("$")) {
                // command
                if (output.isNotEmpty()) {
                    // Store children
                    addChildren(output, currentNode)
                    // clear previous output
                    output = mutableListOf()
                }

                // Perform command
                val command = line.split(" ")[1]

                if(command == "cd") {
                    val arg = line.split(" ")[2]
                    // cd - change currentNode
                    currentNode = goTo(currentNode, arg, )
                }
            } else {
                // output
                output.add(line)
            }
        }
        if (output.isNotEmpty()) {
            // Store children
            addChildren(output, currentNode)
        }

//        println(root)

        // Get directories of at most 100000
        root.getSize(true)
        val boundary = 100000
        allDirs.filter{ it.getSize() <= boundary }.forEach{ println("${it.name} ${it.getSize()}")}
        return allDirs.map{ it.getSize()}.filter{ it <= boundary }.sum()
    }

    fun goTo(currentNode: Node, arg: String) : Node {
        return when(arg) {
            ".." -> currentNode.parent!! //go up
            "/" -> currentNode //do nothing
            else -> currentNode.getChild(arg)!!
        }
    }

    fun addChildren(output: List<String>, currentNode: Node) {
        for(child in output) {
            val name = child.split(" ")[1]
            if(child.startsWith("dir")) {
                val newDir = Directory(name, currentNode)
                currentNode.addChild(newDir)
                allDirs.add(newDir)
            } else {
                val nodeSize = child.split(" ")[0].toInt()
                currentNode.addChild(MyFile(name, nodeSize, currentNode))
            }
        }
    }

    override fun part2(input: List<String>): Int {
        val requiredFreeSpace = 30000000
        val totalSpace = 70000000

        val root = Directory("/")
        allDirs = mutableListOf(root)
        var currentNode: Node = root
        var output: MutableList<String> = mutableListOf()

        // Determine file structure
        for(line in input) {
            if (line.startsWith("$")) {
                // command
                if (output.isNotEmpty()) {
                    // Store children
                    addChildren(output, currentNode)
                    // clear previous output
                    output = mutableListOf()
                }

                // Perform command
                val command = line.split(" ")[1]

                if(command == "cd") {
                    val arg = line.split(" ")[2]
                    // cd - change currentNode
                    currentNode = goTo(currentNode, arg, )
                }
            } else {
                // output
                output.add(line)
            }
        }
        if (output.isNotEmpty()) {
            // Store children
            addChildren(output, currentNode)
        }

        // Get smallest directory that when deleted ensures; unusedSpace <= requiredFreeSpace
        val unusedSpace = totalSpace - root.getSize(true)
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

interface Node {
    val parent : Node?
    var name : String
    fun getSize(cache: Boolean = false) : Int
    fun addChild(child: Node)
    fun getChild(name: String) : Node?
}

class MyFile(override var name: String, var fileSize: Int, override val parent: Node? = null) : Node {
    override fun getSize(cache: Boolean): Int {
        return fileSize
    }

    override fun addChild(child: Node) {
        // Do nothing
    }

    override fun getChild(name: String): Node? {
        return null
    }

    override fun toString(): String {
        return "- $name (file, size=$fileSize)"
    }
}

class Directory(override var name: String, override val parent: Node? = null) : Node {
    var children: MutableList<Node> = mutableListOf()
    var cachedSize : Int? = null

    override fun addChild(child: Node) {
        children.add(child)
    }

    override fun getChild(name: String) : Node? {
        for(child in children) {
            if (child.name == name) {
                return child
            }
        }
        return null
    }

    override fun getSize(cache: Boolean): Int {
        if (cache) {
            if (cachedSize == null) {
                cachedSize = children.sumOf { it.getSize(cache) }
            }
            return cachedSize!!
        }
        if (cachedSize != null) {
            return cachedSize!!
        }
        return children.sumOf { it.getSize() }
    }

    override fun toString(): String {
        var result = "- $name (dir)\n"
        result += children.map { child -> child.toString().prependIndent(" ") }.joinToString("\n")
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
