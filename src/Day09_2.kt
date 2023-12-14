import java.io.File

fun main() {
    var sum = 0

    File( "src/Day09.txt" ).forEachLine { line ->
        line.split( " " ).map { split ->
            split.toInt()
        }.let { history  ->
            var previousValues = history.first()
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

                previousValues += sequence.first() * if ( history.size % 2 == sequence.size % 2 ) 1 else -1
            } // while

            sum += previousValues
        } // map.let
    } // File.forEachLine

    println( sum )
} // fun main