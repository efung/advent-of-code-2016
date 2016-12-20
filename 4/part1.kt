import kotlin.comparisons.compareBy
import java.util.Comparator

val encryptedRE = "([a-z-]+)-(\\d+)\\[([a-z]+)\\]".toRegex();

// Returns
//   - sector ID, if room is real;
//   - 0, if room is phony
fun isReal(room: String): Int {
    var charFreq = mutableMapOf<Char, Int>()
    val matches: MatchGroupCollection? = encryptedRE.matchEntire(room)?.groups
    val name: String = matches!!.get(1)!!.value
    val sectorId: Int = matches.get(2)!!.value.toInt()
    val checksum: String = matches.get(3)!!.value
    
    for (char in name.toCharArray().filter(Char::isLetter)) {
        charFreq[char] = charFreq.getOrElse(char, {0}).plus(1)
    }
    
    val commonCharList : List<Char> = charFreq.keys.sortedWith(object : Comparator<Char> {
       override fun compare(x : Char, y: Char) = 
        if (charFreq.get(x) == charFreq.get(y)) {
            x.compareTo(y)
        } else {
            charFreq.getOrElse(y, {0}) - charFreq.getOrElse(x, {0})
        }
    })
            
    val commonChars : String = commonCharList.slice(0..4).joinToString("")
    
    return if (commonChars == checksum) sectorId else 0

}

fun part1(args: Array<String>) {
	val sectorSum = input.fold(0) {
    	sum, line -> (sum + isReal(line))
    }
    println("Sum of sector IDs: $sectorSum");
}

fun main(args: Array<String>) {
    part1(args)
    part2(args)
}