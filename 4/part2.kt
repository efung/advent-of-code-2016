
//val encryptedRE = "([a-z-]+)-(\\d+)\\[([a-z]+)\\]".toRegex();

val aAsInt = 'a'.toInt()

fun decrypt(room: String, rotate: Int): String {
	val decrypted = room.map { if (it.isLetter()) it.toInt().minus(aAsInt).plus(rotate).mod(26).plus(aAsInt).toChar() else ' ' }
    return decrypted.joinToString("")
}

fun part2(args: Array<String>) {
	for (line in input) {
    	val matches = encryptedRE.matchEntire(line)!!.groupValues
        val name = matches[1]
        val id = matches[2].toInt()
		println("$id == ${decrypt(name, id)}")
    }
}
