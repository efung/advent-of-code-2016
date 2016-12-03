//     1
//   2 3 4
// 5 6 7 8 9
//   A B C
//     D
     
val Keypad2: Map<String, String> = mapOf(
    "1" to "1131",
    "2" to "2362",
    "3" to "1472",
    "4" to "4483",
    "5" to "5655",
    "6" to "27A5",
    "7" to "38B6",
    "8" to "49C7",
    "9" to "9998",
    "A" to "6BAA",
    "B" to "7CDA",
    "C" to "8CCB",
    "D" to "BDDD"
)
fun part2(args: Array<String>) {
    var digit: Char = '5'
    print("Part 2: ")
    for (line in input) {
        for (move in line.toCharArray()) {
            val digitMap = Keypad2[digit.toString()]!!.toCharArray()
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

fun main(args: Array<String>) {
    part1(args)
    part2(args)
}
