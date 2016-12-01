private fun move(curDir: Direction, move: String?): Direction {
    when {
        (curDir == Direction.NORTH && move == "L") -> return Direction.WEST
        (curDir == Direction.NORTH && move == "R") -> return Direction.EAST
        (curDir == Direction.SOUTH && move == "L") -> return Direction.EAST
        (curDir == Direction.SOUTH && move == "R") -> return Direction.WEST
        (curDir == Direction.WEST && move == "L") -> return Direction.SOUTH
        (curDir == Direction.WEST && move == "R") -> return Direction.NORTH
        (curDir == Direction.EAST && move == "L") -> return Direction.NORTH
        (curDir == Direction.EAST && move == "R") -> return Direction.SOUTH
    }
    return curDir
}

fun main(args: Array<String>) {
    val turnRe: Regex = "(L|R)(\\d+)".toRegex()
    var X: Long = 0
    var Y: Long = 0
    var curDir: Direction = Direction.NORTH
    var breadcrumbs: MutableMap<String, Int> = mutableMapOf(Pair("0,0", 1))
    
    val moves = input.split(", ")
   
    moves@ for (move in moves) {
        val matches: MatchGroupCollection? = turnRe.matchEntire(move)?.groups
        val turn: String? = matches?.get(1)?.value
        val distance: Int? = matches?.get(2)?.value?.toInt()
        
        curDir = move(curDir, turn)
        for (i in 1..distance!!) {
            when {
                curDir == Direction.NORTH -> Y += 1
                curDir == Direction.SOUTH -> Y -= 1
                curDir == Direction.WEST -> X -= 1
                curDir == Direction.EAST -> X += 1
            }
            val newPos: String = "$X,$Y"
            if (breadcrumbs.get(newPos) != null) {
                break@moves
            }
            breadcrumbs.put(newPos, 1)
        }
    }
    
    val taxicabDistance: Long = Math.abs(X) + Math.abs(Y)
    print("We revisited ($X, $Y) == $taxicabDistance blocks away")
}
