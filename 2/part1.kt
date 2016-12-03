// 1 2 3
// 4 5 6
// 7 8 9

val UP: Int = 0
val RIGHT: Int = 1
val DOWN: Int = 2
val LEFT: Int = 3

val Keypad: Map<String, String> = mapOf(
    "1" to "1241",
    "2" to "2351",
    "3" to "3362",
    "4" to "1574",
    "5" to "2684",
    "6" to "3695",
    "7" to "4877",
    "8" to "5987",
    "9" to "6998"
)
fun part1(args: Array<String>) {
    var digit: Char = '5';
    print("Part 1: ")
    for (line in input) {
        for (move in line.toCharArray()) {
            val digitMap = Keypad[digit.toString()]!!.toCharArray()
            when (move) {
                'U' -> digit = digitMap[UP]
                'R' -> digit = digitMap[RIGHT]
                'D' -> digit = digitMap[DOWN]
                'L' -> digit = digitMap[LEFT]
            }
        }
        print(digit)
    }
    println()
}

