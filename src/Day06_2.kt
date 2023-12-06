import java.io.File

fun main() {
    File( "src/Day06.txt" ).useLines { seqLines ->
        seqLines.toList()
    }.let { lines ->
        val colonSpaces = ": +".toRegex()
        val spaces = " +".toRegex()
        val empty = ""

        Pair(
            // "Time:      7  15   30"
            lines[ 0 ].split( colonSpaces )[ 1 ].replace( spaces, empty ).toLong(),
            // "Distance:  9  40  200"
            lines[ 1 ].split( colonSpaces )[ 1 ].replace( spaces, empty ).toLong()
        ).let { timeDistance ->
            println(
                ( 1..< timeDistance.first ).count { pressed ->
                    ( timeDistance.first - pressed ) * pressed > timeDistance.second
                } // LongRange.count
            ) // println
        } // Pair.let
    } // useLines.let
} // fun main