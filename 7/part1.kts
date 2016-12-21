package Day7

import java.io.File

fun isABBA(input: String): Boolean {
    return input[0] == input[3] && input[1] == input[2] && input[0] != input[1]
}

fun hasABBA(input: String): Boolean {
    if (input.length < 4) {
        return false
    }

    var i:Int = 0
    while (i <= input.length - 4) {
        if (isABBA(input.substring(i, i+4))) {
            return true
        }
        i++
    }
    return false
}

val count = File("input.txt").readLines().count {
    // Assumes line will never begin with [, and there are never
    // any adjacent [abcd] groups
    val groups = it.split("[", "]")

    groups.filterIndexed { i, S -> i % 2 == 0 }.any { hasABBA(it) } &&
    !groups.filterIndexed { i, S -> i % 2 == 1 }.any { hasABBA(it) }
}

println(count)
