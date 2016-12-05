
// a,b,c sidelengths, with c largest
// Then a + b > c --> (a+b+c)/2 > c
val LINE_RE : Regex = "\\s*(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*".toRegex()

fun part1(args: Array<String>) {
    var numValid : Int = 0
    for (line in input) {
        if (isValid(line)) {
            numValid++    
        }
    }
    println("Possible triangles: $numValid")
}

fun isValid(line : String) : Boolean {
    val matchGroups: MatchGroupCollection? = LINE_RE.matchEntire(line)?.groups

    val sideLengths: List<Int> = matchGroups?.drop(1)!!.map{ it!!.value.toInt() }
    val max : Float = sideLengths.max()!!.toFloat()

    val sumLengthsHalf = sideLengths.sumBy { it }.div(2.0f)
    return sumLengthsHalf > max    
}

fun main(args: Array<String>) {
    part1(args)
    part2(args)
}
