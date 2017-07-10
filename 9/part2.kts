package Day9

import Day9.Part2.State.*
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader
import java.io.StringReader

sealed class State {
    object MARKER_START : State()
    object MARKER_END : State()
    object BEGIN_COUNT : State()
    object END_COUNT : State()
    object BEGIN_REPEAT: State()
    object BEGIN_DECOMPRESS : State()
    object END_DECOMPRESS : State()
    object UNKNOWN : State()
    object EOF : State()
}


class Part2(val reader: Reader) {
    var state: State = UNKNOWN
    var currentCount: Int = 0
    var currentRepeat: Int = 0
    var decompressLength: Long = 0

    fun outputLength() : Long {
        while (state != EOF) {
            state = when (state) {
                is UNKNOWN -> peek()
                is MARKER_START -> consumeMarkerStart()
                is BEGIN_COUNT -> consumeCount()
                is END_COUNT -> consumeMarkerSeparator()
                is BEGIN_REPEAT -> consumeRepeat()
                is MARKER_END -> consumeMarkerEnd()
                is BEGIN_DECOMPRESS -> decompress()
                is END_DECOMPRESS -> UNKNOWN
                else -> UNKNOWN
            }
        }
        return decompressLength
    }

    internal fun peek() : State {
        val char = reader.read()
        if (char == -1) {
            return EOF
        }
        when (char.toChar()) {
            '(' -> {
                return BEGIN_COUNT
            }
            ' ', '\t', '\n', '\r' -> return UNKNOWN
            else -> {
                decompressLength++
                return UNKNOWN
            }
        }
    }

    internal fun consumeMarkerStart() : State {
        val char = reader.read().toChar()
        if (char != '(') {
            println("Expected marker start but got ${char}")
        }
        return BEGIN_COUNT
    }

    internal fun consumeCount() : State {
        val intString = readInteger(reader)

        currentCount = Integer.valueOf(intString)
        return END_COUNT
    }

    internal fun consumeMarkerSeparator() : State {
        val x = reader.read().toChar()
        if (x != 'x') {
            println("Expected an 'x' but got ${x}")
        }
        return BEGIN_REPEAT
    }

    internal fun consumeRepeat() : State {
        val intString = readInteger(reader)
        currentRepeat = Integer.valueOf(intString)
        return MARKER_END
    }

    internal fun consumeMarkerEnd() : State {
        val char = reader.read().toChar()
        if (char != ')') {
            println("Expected marker end but got ${char}")
        }
        return BEGIN_DECOMPRESS
    }

    internal fun decompress() : State {
        val buf = CharArray(currentCount)
        val readCount = reader.read(buf)
        if (readCount != currentCount) {
            println("Expected to read ${currentCount} but only got ${readCount}")
        }
        val bufReader = StringReader(String(buf))
        decompressLength += currentRepeat * Part2(bufReader).outputLength()
        return END_DECOMPRESS
    }

    internal fun readInteger(reader: Reader) : Int {
        var accum = 0
        do {
            reader.mark(1)
            val readInt = reader.read()
            if (readInt != -1) {
                val char = readInt.toChar().toString()
                try {
                    val d = Integer.valueOf(char)
                    accum = 10 * accum + d
                } catch (nfe: NumberFormatException) {
                    reader.reset()
                    return accum
                }
            }
        } while (readInt != -1)
        return accum
    }
}

//val input = StringReader("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")
val input = BufferedReader(FileReader("input.txt"))
println(Part2(input).outputLength())

