package Day8

import java.io.File

fun Array<Array<Boolean>>.printAsScreen() {
    this.forEach { it ->
        it.forEach { itinner ->
            print( if (itinner == false) "." else "#" )
        }
        println()
    }
    println()
}

fun Array<Array<Boolean>>.rect(wide: Int, tall: Int) {
    for (r in 0..tall-1) {
        for (c in 0..wide-1) {
            this[r][c] = true
        }
    }
}

fun Array<Array<Boolean>>.rotateColumnDown(index: Int, amt: Int) {
    val numRows = this.size
    val shiftedOut: Array<Boolean> = Array(amt, {r -> false})
    var i: Int = 0
    for (r in numRows-amt..numRows-1) {
        shiftedOut[i++] = this[r][index]
    }

    for (r in numRows-1 downTo amt) {
        this[r][index] = this[r-amt][index]
    }
    for (r in 0..shiftedOut.size-1) {
        this[r][index] = shiftedOut[r]
    }
}

fun Array<Array<Boolean>>.rotateRowRight(index: Int, amt: Int) {
    val row = this[index]
    val shiftedOut = row.sliceArray(row.size - amt..row.size - 1)

    for (c in row.size-1 downTo amt) {
        this[index][c] = this[index][c-amt]
    }
    for (c in 0..shiftedOut.size-1) {
        this[index][c] = shiftedOut[c]
    }
}

// An array of rows of elements, i.e. screen[a][b] is the a'th row, b'th column
// 0 is top row, 0 is left column
val screen: Array<Array<Boolean>> = Array(6, {row -> Array<Boolean>(50, {col -> false})})

val rectRE: Regex = Regex("rect (\\d+)x(\\d+)")
val colRE: Regex = Regex("rotate column x=(\\d+) by (\\d+)")
val rowRE: Regex = Regex("rotate row y=(\\d+) by (\\d+)")

File("input.txt").forEachLine() { line ->
    if (line.startsWith("rect")) {
        val parts = rectRE.matchEntire(line)!!.groupValues
        screen.rect( parts[1].toInt(), parts[2].toInt() )
    } else if (line.startsWith("rotate column")) {
        val parts = colRE.matchEntire(line)!!.groupValues
        screen.rotateColumnDown( parts[1].toInt(), parts[2].toInt() )
    } else if (line.startsWith("rotate row")) {
        val parts = rowRE.matchEntire(line)!!.groupValues
        screen.rotateRowRight( parts[1].toInt(), parts[2].toInt() )
    }
}

screen.printAsScreen()
