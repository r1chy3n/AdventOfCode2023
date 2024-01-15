import java.io.File

fun main() {
    val pathname = "src/Day13.txt"

    var number = 0

    val horizontal: (List<String>) -> Int = { pattern ->
        var num = 0
        var i = 1

        while ( num == 0 && i < pattern.size ) {
            while ( i < pattern.size && pattern[ i - 1 ] != pattern[ i ]) {
                i++
            } // while

            var upper: List<String>
            var lower: List<String>

            if ( i < pattern.size ) {
                upper = pattern.take( i )
                lower = pattern.drop( i )

                val dropN: Int

                if ( upper.size > lower.size ) {
                    dropN = upper.size - lower.size

                    upper = upper.drop( dropN )
                } else if ( upper.size < lower.size ) {
                    dropN = lower.size - upper.size

                    lower = lower.dropLast( dropN )
                } // if - else i_f

                if ( upper == lower.reversed()) {
                    num = i
                } else {
                    i++
                } // if - else
            } // if
        } // while

        num
    } // fun horizontal

    val vertical: (List<String>) -> Int = { pattern ->
        val empty = ""
        val transPattern = mutableListOf<String>()

        pattern[ 0 ].indices.forEach { index ->
            transPattern.add(
                pattern.map { line ->
                    line[ index ]
                }.joinToString( empty )
            ) // transPattern.add
        } // indices.forEach

        horizontal( transPattern )
    } // fun horizontal

    val getNumber: (List<String>) -> Int = { pattern ->
        horizontal( pattern ) * 100 + vertical( pattern )
    } // fun getNumber

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