enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

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

fun part1main(args: Array<String>) {
    val turnRe: Regex = "(L|R)(\\d+)".toRegex()
    var X: Long = 0
    var Y: Long = 0
    var curDir: Direction = Direction.NORTH
    
    val moves = input.split(", ")
   
    for (move in moves) {
        val matches: MatchGroupCollection? = turnRe.matchEntire(move)?.groups
        val turn: String? = matches?.get(1)?.value
        val distance: Int? = matches?.get(2)?.value?.toInt()
        
        curDir = move(curDir, turn)
        when (curDir) {
            Direction.NORTH -> Y += distance!!
            Direction.SOUTH -> Y -= distance!!
            Direction.WEST -> X -= distance!!
            Direction.EAST -> X += distance!!
        }
	}
    
    val taxicabDistance: Long = Math.abs(X) + Math.abs(Y)
    println("We're now at ($X, $Y) == $taxicabDistance blocks away")
}