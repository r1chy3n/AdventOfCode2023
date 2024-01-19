import java.io.File

fun main() {
    var totalLoad = 0

    val pathname = "src/Day14.txt"
    val lines = File( pathname ).readLines()
    val empty = ""
    val cCube = '#'
    val sCube = "#"
    val round = 'O'

    lines.first().indices.forEach { colIndex ->
        lines.map { line ->
            line[colIndex]
        }.joinToString( empty ).split( sCube ).joinToString( sCube ) { section ->
            section.toCharArray().sortedWith { c1, c2 ->
                if (c1 == c2 || c1 == cCube || c2 == cCube) {
                    0
                } else if (c1 == round) {
                    -1
                } else {
                    1
                } // if - else i_f - else
            }.joinToString( empty )
        }.forEachIndexed { index, c ->
            if ( c == round ) {
                totalLoad += lines.size - index
            } // if
        } // forEachIndexed
    } // forEach

    println( "total load = $totalLoad" )
} // fun main