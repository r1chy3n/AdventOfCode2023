import java.io.File

fun main() {
    val pathname = "src/Day13.txt"

    val horizontal: (List<String>) -> Int = { pattern ->
        val zero = 0

        var number = 0

        var mirrorStart: Int

        0.rangeUntil( pattern.lastIndex ).firstOrNull { i ->
            mirrorStart = i + 1

            i.downTo( zero ).zip( mirrorStart.rangeTo( pattern.lastIndex )).sumOf {(upper, lower) ->
                pattern[ upper ].indices.count { j ->
                    pattern[ upper ][ j ] != pattern[ lower ][ j ]
                } // indices.count
            } == 1
        }?.let { index ->
            number = index + 1
        } // firstOrNull?.let

        number
    } // fun horizontal

    val vertical: (List<String>) -> Int = { pattern ->
        val empty = ""

        val vPattern = pattern.first().indices.map { colIndex ->
            pattern.map { line ->
                line[ colIndex ]
            }.joinToString( empty )
        } // indices.map

        horizontal( vPattern )
    } // fun vertical

    val getNumber: (List<String>) -> Int = { pattern ->
        var number = horizontal( pattern ) * 100

        if ( number == 0 ) {
            number = vertical( pattern )
        } // if

        number
    } // fun getNumber

    var number = 0
    var remainLines = File( pathname ).readLines()

    var emptyIndex: Int
    var dropN: Int

    while ( remainLines.contains( "" )) {
        emptyIndex = remainLines.indexOf( "" )
        number += getNumber( remainLines.take( emptyIndex ))
        dropN = emptyIndex + 1
        remainLines = remainLines.drop( dropN )
    } // while

    number += getNumber( remainLines )

    println( "number = $number" )
} // fun main
