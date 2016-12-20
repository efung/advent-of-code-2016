package Day5

import java.io.File
import java.security.MessageDigest

val input: String = File("input.txt").readText().trim()

val HEX_CHARS = "0123456789ABCDEF".toCharArray()

fun ByteArray.toHex() : String{
    val result = StringBuffer()

    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }

    return result.toString()
}


fun getDigest(md: MessageDigest, input: String): ByteArray {
    return md.digest(input.toByteArray(Charsets.UTF_8));
}

fun part1(input: String) {
    val md: MessageDigest = MessageDigest.getInstance("MD5");
    
    var index: Int = 0
    for (i in 1..8) {
        var hash: ByteArray
        do {
            hash = getDigest(md, input + index++)
        } while (
            hash[0].compareTo(0) != 0
            || hash[1].compareTo(0) != 0
            || (hash[2].toInt() ushr 4) .compareTo(0) != 0
        )
        
        print(hash.toHex()[5])
    }
    println()
}


part1(input)
