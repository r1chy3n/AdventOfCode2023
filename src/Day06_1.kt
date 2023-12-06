import java.io.File

fun main() {
    var multiply = 1

    val timeDistances = mutableListOf<Pair<Int, Int>>()

    File( "src/Day06.txt" ).useLines { seqLines ->
        val lines = seqLines.toList()
        val colonSpaces = ": +".toRegex()
        val spaces = " +".toRegex()

        // "Time:      7  15   30"
        val times = lines[ 0 ].split( colonSpaces )[ 1 ].split( spaces )

        // "Distance:  9  40  200"
        lines[ 1 ].split( colonSpaces )[ 1 ].split( spaces ).forEachIndexed { index, distance ->
            timeDistances.add( Pair( times[ index ].toInt(), distance.toInt()))
        } // split.forEachIndexed
    } // File.useLines

    timeDistances.forEach { timeDistance ->
        multiply *= ( 1..< timeDistance.first ).count { pressed ->
            ( timeDistance.first - pressed ) * pressed > timeDistance.second
        } // IntRange.count
    } // timeDistances.forEach

    println( multiply )
} // fun main