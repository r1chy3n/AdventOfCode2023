import java.io.File

fun main() {
    var totalLoad = 0

    val cycleCount = 1000000000
    val empty = ""
    val sCube = "#"
    val cCube = '#'
    val round = 'O'

    val v2H: (List<String>) -> List<String> = { pattern ->
        pattern.first().indices.map { colIndex ->
            pattern.map { line ->
                line[ colIndex ]
            }.joinToString( empty )
        } // map
    } // fun v2H

    val roll: (List<String>) -> List<String> = { pattern ->
        pattern.map { line ->
            line.split( sCube ).joinToString( sCube ) { section ->
                section.toCharArray().sortedWith { c1, c2 ->
                    if (c1 == c2 || c1 == cCube || c2 == cCube) {
                        0
                    } else if (c1 == round) {
                        -1
                    } else {
                        1
                    } // if - else i_f - else
                }.joinToString( empty )
            } // joinToString
        } // map
    } // fun roll

    val tiltNorth: (List<String>) -> List<String> = { pattern ->
        v2H( roll( v2H( pattern )))
    } // fun tiltNorth

    val tiltSouth: (List<String>) -> List<String> = { pattern ->
        v2H( roll( v2H( pattern.reversed()))).reversed()
    } // fun tiltSouth

    val tiltEast: (List<String>) -> List<String> = { pattern ->
        roll( pattern.map { line ->
            line.reversed()
        }).map { line ->
            line.reversed()
        }
    }

    val cycle: (List<String>) -> List<String> = { pattern ->
        tiltEast( tiltSouth( roll( tiltNorth( pattern ))))
    } // fun cycle

    val pathname = "src/Day14.txt"
    val history = mutableListOf<List<String>>()

    var lines = File( pathname ).readLines()

    var cycled: List<String>
    var repeatIndex: Int
    var repeatSize: Int
    var historyIndex: Int

    1.rangeTo( cycleCount ).firstNotNullOf {
        cycled = cycle( lines )

        if ( history.contains( cycled )) {
            repeatIndex = history.indexOf( cycled )
            repeatSize = history.size - repeatIndex
            historyIndex = ( cycleCount - repeatIndex ) % repeatSize + repeatIndex - 1

            history[ historyIndex ]
        } else {
            history.add( cycled )

            lines = cycled

            null
        } // if - else
    }.forEachIndexed { index, line ->
        line.forEach { c ->
            if ( c == round ) {
                totalLoad += lines.size - index
            } // if
        } // forEachIndexed
    } // forEach

    println( "total load = $totalLoad" )
} // fun main