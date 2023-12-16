import java.io.File

@Suppress("LocalVariableName")
fun main() {
    File( "src/Day10.txt" ).readLines().let { lines ->
        // 找出 S
        val S = 'S'
        val sY = lines.indexOfFirst { line ->
            line.indexOf( S ) != -1
        } // lines.indexOfFirst
        val sX = lines[ sY ].indexOf( S )
        val part1 = mutableSetOf<Pair<Int, Int>>()
        val part2 = mutableSetOf<Pair<Int, Int>>()
        val loops = mutableSetOf( Pair( sX, sY ))
        val V = '|'
        val H = '-'
        val L = 'L'
        val J = 'J'
        val _7 = '7'
        val F = 'F'

        var northPart = part1
        var southPart = part2
        var eastPart = part1
        var westPart = part2

        val add2VPart = { progression: IntProgression,
                          x: Int,
                          adding: MutableSet<Pair<Int, Int>>,
                          removing: MutableSet<Pair<Int, Int>> ->
            progression.takeWhile { y ->
                !loops.contains( Pair( x, y ))
            }.forEach { y ->
                Pair( x, y ).let {
                    removing.remove( it )
                    adding.add( it )
                } // Pair.let
            } // takeWhile.forEach
        } // fun

        val splitV = { x: Int, y: Int ->
            // x, y 的上半邊
            if ( y > 0 ) {
                add2VPart(( y - 1 ).downTo( 0 ), x, northPart, southPart )
            } // if

            // x, y 的下半邊
            if ( y < lines.lastIndex ) {
                add2VPart(( y + 1 ).rangeTo( lines.lastIndex ), x, southPart, northPart )
            } // if
        } // fun

        val add2HPart = { progression: IntProgression,
                          y: Int,
                          adding: MutableSet<Pair<Int, Int>>,
                          removing: MutableSet<Pair<Int, Int>> ->
            progression.takeWhile { x ->
                !loops.contains( Pair( x, y ))
            }.forEach { x ->
                Pair( x, y ).let {
                    removing.remove( it )
                    adding.add( it )
                } // Pair.let
            } // takeWhile.forEach
        } // fun

        val splitH = { x: Int, y: Int ->
            // x, y 的右半邊
            if ( x < lines[ y ].lastIndex ) {
                add2HPart(( x + 1 ).rangeTo( lines[ y ].lastIndex ), y, eastPart, westPart )
            } // if

            // x, y 的左半邊
            if ( x > 0 ) {
                add2HPart(( x - 1 ).downTo( 0 ), y, westPart, eastPart )
            } // if
        } // fun splitH

        val swapV = {
            northPart = southPart.also {
                southPart = northPart
            } // south.also
        } // fun swapV

        val swapH = {
            eastPart = westPart.also {
                westPart = eastPart
            } // south.also
        } // fun swapV

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
            Pair( nextX, nextY ).let {
                loops.add( it )
                part1.remove( it )
                part2.remove( it )
            } // Pair.let

            when ( enter ) {
                Direction.NORTH -> {
                    splitH( nextX, nextY )

                    when ( lines[ nextY ][ nextX ]) {
                        J -> {
                            if ( westPart != northPart ) {
                                swapV()
                            } // if

                            splitV( nextX, nextY )

                            enter = Direction.EAST

                            nextX--
                        } // J

                        V -> nextY++

                        L -> {
                            if ( eastPart != northPart ) {
                                swapV()
                            } // if

                            splitV( nextX, nextY )

                            enter = Direction.WEST

                            nextX++
                        } // L
                    } // when
                } // NORTH

                Direction.EAST -> {
                    splitV( nextX, nextY )

                    when ( lines[ nextY ][ nextX ]) {
                        L -> {
                            if ( northPart != eastPart ) {
                                swapH()
                            } // if

                            splitH( nextX, nextY )

                            enter = Direction.SOUTH

                            nextY--
                        } // L

                        H -> nextX--

                        F -> {
                            if ( southPart != eastPart ) {
                                swapH()
                            } // if

                            splitH( nextX, nextY )

                            enter = Direction.NORTH

                            nextY++
                        } // F
                    } // when
                } // EAST

                Direction.SOUTH -> {
                    splitH( nextX, nextY )

                    when ( lines[ nextY ][ nextX ]) {
                        _7 -> {
                            if ( westPart != southPart ) {
                                swapV()
                            } // if

                            splitV( nextX, nextY )

                            enter = Direction.EAST

                            nextX--
                        } // _7

                        V -> nextY--

                        F -> {
                            if ( eastPart != southPart ) {
                                swapV()
                            } // if

                            splitV( nextX, nextY )

                            enter = Direction.WEST

                            nextX++
                        } // F
                    } // when

                } // SOUTH

                Direction.WEST -> {
                    splitV( nextX, nextY )

                    when ( lines[ nextY ][ nextX ]) {
                        J -> {
                            if ( westPart != northPart ) {
                                swapH()
                            } // if

                            splitH( nextX, nextY )

                            enter = Direction.SOUTH

                            nextY--
                        } // J

                        H -> nextX++

                        _7 -> {
                            if ( westPart != southPart ) {
                                swapH()
                            } // if

                            splitH( nextX, nextY )

                            enter = Direction.NORTH

                            nextY++
                        } // _7
                    } // when
                } // WEST
            } // when
        } // while

        println(
            if (
                part1.any { xy ->
                    xy.first == 0
                            || xy.first == lines[ 0 ].lastIndex
                            || xy.second == 0
                            || xy.second == lines.lastIndex
                }
            ) part2.count() else part1.count()
        ) // println
    } // readLines.let
} // fun main
