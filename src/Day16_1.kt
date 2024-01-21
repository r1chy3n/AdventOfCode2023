import java.io.File

fun main() {
    val pathname = "src/Day16.txt"

    val contraption = File( pathname ).readLines()
    val energized = List( contraption.size ) { lineIndex ->
        IntArray( contraption[ lineIndex ].length ) {
            0
        } // IntArray
    } // List

    val beams = mutableListOf( Pair( Pair( 0, 0 ), Pair( 1, 0 )))
    val history = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    val right = Pair( 1, 0 )
    val left = Pair( -1, 0 )
    val up = Pair( 0, -1 )
    val down = Pair( 0, 1 )

    var beam: Pair<Pair<Int, Int>, Pair<Int, Int>>
    var grid: Char

    val addOrNot: (Int, Int, Pair<Int, Int>) -> Unit = { x, y, d ->
        if (
            contraption.first().indices.contains( x )
            && contraption.indices.contains( y )
        ) {
            beams.add( Pair( Pair( x, y ), d ))
        }
    }

    while ( beams.isNotEmpty()) {
        beam = beams.removeFirst()

        if ( !history.contains( beam )) {
            energized[ beam.first.second ][ beam.first.first ] = 1
            grid = contraption[ beam.first.second ][ beam.first.first ]

            when ( grid ) {
                '.' -> addOrNot(
                    beam.first.first + beam.second.first,
                    beam.first.second + beam.second.second,
                    beam.second
                )
                '/' -> when ( beam.second ) {
                    right -> addOrNot(
                        beam.first.first,
                        beam.first.second + up.second,
                        up
                    )
                    left -> addOrNot(
                        beam.first.first,
                        beam.first.second + down.second,
                        down
                    )
                    up -> addOrNot(
                        beam.first.first + right.first,
                        beam.first.second,
                        right
                    )
                    down -> addOrNot(
                        beam.first.first + left.first,
                        beam.first.second,
                        left
                    )
                    else -> {}
                } // when
                '\\' ->  when ( beam.second ) {
                    right -> addOrNot(
                        beam.first.first,
                        beam.first.second + down.second,
                        down
                    )
                    left -> addOrNot(
                        beam.first.first,
                        beam.first.second + up.second,
                        up
                    )
                    up -> addOrNot(
                        beam.first.first + left.first,
                        beam.first.second,
                        left
                    )
                    down -> addOrNot(
                        beam.first.first + right.first,
                        beam.first.second,
                        right
                    )
                    else -> {}
                } // when
                '|' -> when ( beam.second ) {
                    right, left -> {
                        addOrNot(
                            beam.first.first,
                            beam.first.second + up.second,
                            up
                        )
                        addOrNot(
                            beam.first.first,
                            beam.first.second + down.second,
                            down
                        )
                    } // right, left
                    up, down -> addOrNot(
                        beam.first.first,
                        beam.first.second + beam.second.second,
                        beam.second
                    )
                    else -> {}
                } // when
                '-' -> when ( beam.second ) {
                    right, left -> addOrNot(
                        beam.first.first + beam.second.first,
                        beam.first.second,
                        beam.second
                    )
                    up, down -> {
                        addOrNot(
                            beam.first.first + left.first,
                            beam.first.second,
                            left
                        )
                        addOrNot(
                            beam.first.first + right.first,
                            beam.first.second,
                            right
                        )
                    } // 0, 1
                    else -> {}
                } // when
                else -> {}
            } // when

            history.add( beam )
        } // if !history.contain
    } // while

    println(
        energized.sumOf {
            it.sum()
        } // sumOf
    ) // println
} // fun main