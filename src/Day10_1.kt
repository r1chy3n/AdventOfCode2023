import java.io.File

@Suppress("LocalVariableName")
fun main() {
    var steps = 1

    File( "src/Day10.txt" ).readLines().let { lines ->
        // 找出 S
        val S = 'S'
        val sY = lines.indexOfFirst { line ->
            line.indexOf( S ) != -1
        } // lines.indexOfFirst
        val sX = lines[ sY ].indexOf( S )
        val V = '|'
        val H = '-'
        val L = 'L'
        val J = 'J'
        val _7 = '7'
        val F = 'F'

        var nextX: Int
        var nextY: Int
        var enter: Direction

        if (
            sY > 0 && listOf( _7, V, F ).contains( lines[ sY - 1 ][ sX ])
        ) {
            // toward north
            enter = Direction.SOUTH
            nextX = sX
            nextY = sY - 1
        } else if (
            sX < lines[ sY ].lastIndex
            && listOf( J, H, _7 ).contains( lines[ sY ][ sX + 1 ])
        ) {
            // toward east
            enter = Direction.WEST
            nextX = sX + 1
            nextY = sY
        } else {
            // toward south
            enter = Direction.NORTH
            nextX = sX
            nextY = sY + 1
        }

        while ( !( nextX == sX && nextY == sY )) {
            steps++

            when ( enter ) {
                Direction.NORTH -> when ( lines[ nextY ][ nextX ]) {
                    J -> {
                        enter = Direction.EAST
                        nextX--
                    }
                    V -> {
                        nextY++
                    }
                    L -> {
                        enter = Direction.WEST
                        nextX++
                    }
                } // when
                Direction.EAST -> when ( lines[ nextY ][ nextX ]) {
                    L -> {
                        enter = Direction.SOUTH
                        nextY--
                    }
                    H -> {
                        nextX--
                    }
                    F -> {
                        enter = Direction.NORTH
                        nextY++
                    }
                } // when
                Direction.SOUTH -> when ( lines[ nextY ][ nextX ]) {
                    _7 -> {
                        enter = Direction.EAST
                        nextX--
                    }
                    V -> {
                        nextY--
                    }
                    F -> {
                        enter = Direction.WEST
                        nextX++
                    }
                } // when
                Direction.WEST -> when ( lines[ nextY ][ nextX ]) {
                    J -> {
                        enter = Direction.SOUTH
                        nextY--
                    }
                    H -> {
                        nextX++
                    }
                    _7 -> {
                        enter = Direction.NORTH
                        nextY++
                    }
                } // when
            } // when
        } // while
    } // readLines.let

    println( steps / 2 )
} // fun main

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
} // enum class Direction
