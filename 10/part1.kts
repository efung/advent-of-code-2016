import java.io.File

val HI_LO : Regex = "bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)".toRegex()
val GOES_TO : Regex = "value (\\d+) goes to bot (\\d+)".toRegex()

sealed class Disposition {
    data class Bot(val botNumber: Int) : Disposition()
    data class OutputBin(val binNumber: Int) : Disposition()
}

class Bot(val idNum: Int) {
    data class DispositionResult(val disposition: Disposition, val value: Int)

    constructor(idNum: Int, low: Disposition, high: Disposition) : this(idNum) {
        setDispositions(low, high)
    }

    private var chips: MutableList<Int> = mutableListOf()
    lateinit var lowDisposition: Disposition
    lateinit var highDisposition: Disposition

    fun setDispositions(low : Disposition, high : Disposition) {
        lowDisposition = low
        highDisposition = high
    }

    fun passChip(chip: Int) = chips.add(chip)

    fun canProceed(): Boolean = chips.size == 2

    fun execute(): Pair<DispositionResult, DispositionResult> {
        val low = chips.min()!!
        val high = chips.max()!!
        chips.clear()
        return Pair(DispositionResult(lowDisposition, low), DispositionResult(highDisposition, high))
    }
}

val factory: MutableMap<Int, Bot> = mutableMapOf()

fun passChipToBot(botNum: Int, value: Int) {
    val bot: Bot
    if (factory.containsKey(botNum)) {
        bot = factory.getValue(botNum)
    } else {
        bot = Bot(botNum)
        factory[botNum] = bot
    }
    bot.passChip(value)
}

File("input.txt").forEachLine() { line ->
    if (line.startsWith("bot")) {
        val parts = HI_LO.matchEntire(line)!!.groupValues
        val botNum = parts[1].toInt()
        val lowDisposition = when {
            parts[2] == "bot" -> Disposition.Bot(parts[3].toInt())
            else -> Disposition.OutputBin(parts[3].toInt())
        }
        val highDisposition = when{
            parts[4] == "bot" -> Disposition.Bot(parts[5].toInt())
            else -> Disposition.OutputBin(parts[5].toInt())
        }
        val bot: Bot
        if (factory.containsKey(botNum)) {
            bot = factory.getValue(botNum)
            bot.setDispositions(lowDisposition, highDisposition)
        } else {
            factory[botNum] = Bot(botNum, lowDisposition, highDisposition)
        }

    } else if (line.startsWith("value")) {
        val parts = GOES_TO.matchEntire(line)!!.groupValues
        val botNum = parts[2].toInt()

        passChipToBot(botNum, parts[1].toInt())
    } else {
        println("Unexpected line: ${line}")
    }
}

while (factory.filterValues { it.canProceed() }.count() > 0) {
    factory.filterValues { it.canProceed() }.forEach { _, v ->

        val (lowResult, highResult) = v.execute()
        println("Bot ${v.idNum} comparing ${lowResult.value} and ${highResult.value}")
        when (lowResult.disposition) {
            is Disposition.Bot -> passChipToBot(lowResult.disposition.botNumber, lowResult.value)
//            is Disposition.OutputBin -> null
        }

        when (highResult.disposition) {
            is Disposition.Bot -> passChipToBot(highResult.disposition.botNumber, highResult.value)
//            is Disposition.OutputBin -> null
        }
    }
}