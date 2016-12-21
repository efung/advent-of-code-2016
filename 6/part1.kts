package Day6

import java.io.File

val freqs: Array<MutableMap<Char, Int>> = Array<MutableMap<Char, Int>>(8, { i -> mutableMapOf<Char, Int>() })
val input = File("input.txt").readLines().map { line ->
    line.forEachIndexed { i, char ->
        freqs[i].put(char, (freqs[i].getOrElse(char) {0}) + 1)
    }
}

freqs.forEach { letterMap ->
    print( letterMap.maxBy { it.value }!!.key )
}

