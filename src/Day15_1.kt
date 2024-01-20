import java.io.File

fun main() {
    val pathname = "src/Day15.txt"
    val empty = ""
    val comma = ","
    val steps = File( pathname )
        .readLines().joinToString( empty ).split( comma )

    var sum = 0L

    var current: Long

    steps.forEach { step ->
        current = 0

        step.forEach { char ->
            current += char.code
            current *= 17
            current %= 256
        } // step.forEach

        sum += current
    } // oneLine.forEach

    println( "sum = $sum")
} // fun main