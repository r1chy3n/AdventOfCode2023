import java.io.File

fun main() {
    var sum = 0

    File( "src/Day09.txt" ).forEachLine { line ->
        line.split( " " ).map { split ->
            split.toInt()
        }.let { history  ->
            var nextValues = history.last()
            val sequence = history.toMutableList()

            while (
                sequence.any { value ->
                    value != 0
                }
            ) {
                ( sequence.lastIndex downTo 1 ).forEach { i ->
                    sequence[ i ] -= sequence[ i - 1 ]
                } // Range.forEach

                sequence.removeFirst()

                nextValues += sequence.last()
            } // while

            sum += nextValues
        } // map.let
    } // File.forEachLine

    println( sum )
} // fun main