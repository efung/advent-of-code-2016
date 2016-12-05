fun part2(args: Array<String>) {
    var numValid : Int = 0
    val iterator: Iterator<String> = input.iterator()
    while (iterator.hasNext()) {
        val line1 = iterator.next()
        val line2 = iterator.next()
        val line3 = iterator.next()
        val matchGroups1: MatchGroupCollection? = LINE_RE.matchEntire(line1)?.groups
        val matchGroups2: MatchGroupCollection? = LINE_RE.matchEntire(line2)?.groups
        val matchGroups3: MatchGroupCollection? = LINE_RE.matchEntire(line3)?.groups
        
        if (isValid("${matchGroups1?.get(1)!!.value} ${matchGroups2?.get(1)!!.value} ${matchGroups3?.get(1)!!.value}")) {
            numValid++
        }

        if (isValid("${matchGroups1?.get(2)!!.value} ${matchGroups2?.get(2)!!.value} ${matchGroups3?.get(2)!!.value}")) {
            numValid++
        }

        if (isValid("${matchGroups1?.get(3)!!.value} ${matchGroups2?.get(3)!!.value} ${matchGroups3?.get(3)!!.value}")) {
            numValid++
        }
    } 
    
    println("Possible vertical triangles: $numValid")
}
