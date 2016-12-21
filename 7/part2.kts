package Day7

import java.io.File

fun isABA(input: String): Boolean {
    return input[0] == input[2] && input[0] != input[1]
}

fun findABA(input: String): List<String> {
    var ABAs: MutableList<String> = mutableListOf()
    if (input.length < 3) {
        return ABAs
    }

    var i:Int = 0
    while (i <= input.length - 3) {
        val substr = input.substring(i, i+3)
        if (isABA(substr)) {
            ABAs.add(substr)
        }
        i++
    }
    return ABAs
}

val count = File("input.txt").readLines().count {
    // Assumes line will never begin with [, and there are never
    // any adjacent [abcd] groups
    val groups = it.split("[", "]")

    val supernetABAs = groups.filterIndexed { i, S -> i % 2 == 0 }.flatMap { findABA(it) }
    val hypernetBABs = groups.filterIndexed { i, S -> i % 2 == 1 }.flatMap { findABA(it) }

    supernetABAs.map { "${it[1]}${it[0]}${it[1]}" }.intersect(hypernetBABs).isNotEmpty()
}

println(count)
