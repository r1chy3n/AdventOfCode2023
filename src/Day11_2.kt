import java.io.File

fun main() {
    var sum = 0L

    File( "src/Day11.txt" ).readLines().let { image ->
        val times = 1000000
        val period = '.'
        val pound = '#'
        val emptyCols = mutableListOf<Int>()
        val emptyRows = mutableListOf<Int>()

        // empty columns
        0.rangeTo( image[ 0 ].lastIndex ).forEach { charIndex ->
            if (
                image.map { line ->
                    line[ charIndex ]
                }.all { char ->
                    char == period
                } // map.all
            ) {
                emptyCols.add( charIndex )
            } // if
        } // IntRange.forEach

        // empty rows
        0.rangeTo( image.lastIndex ).forEach { lineIndex ->
            if (
                image[ lineIndex ].all { char ->
                    char == period
                } // CharSequence.all
            ) {
                emptyRows.add( lineIndex )
            } // if
        } // IntRange.forEach

        mutableListOf<Pair<Int, Int>>().also { pounds ->
            image.forEachIndexed { indexL, line ->
                line.forEachIndexed { indexC, char ->
                    if ( char == pound ) {
                        pounds.add( Pair( indexC, indexL))
                    } // if
                } // CharSequence.forEachIndexed
            } // image.forEachIndexed
        }.let { pounds ->
            pounds.forEachIndexed { index, pair ->
                if ( index < pounds.lastIndex ) {
                    ( index + 1 ).rangeTo( pounds.lastIndex ).forEach { otherIndex ->
                        val minX = pair.first.coerceAtMost( pounds[ otherIndex ].first )
                        val maxX = pair.first.coerceAtLeast( pounds[ otherIndex ].first )
                        val diffX = maxX - minX
                        val diffY = pounds[ otherIndex ].second - pair.second

                        var expanded = 0

                        if ( diffX > 1 ) {
                            val rangeX = minX.rangeTo( maxX )

                            expanded += emptyCols.count { col ->
                                rangeX.contains( col )
                            } * ( times - 1 )
                        } // if

                        if ( diffY > 1 ) {
                            val rangeY = pair.second.rangeTo( pounds[ otherIndex ].second )

                            expanded += emptyRows.count { row ->
                                rangeY.contains( row )
                            } * ( times - 1 )
                        } // if

                        sum += diffX + diffY + expanded
                    } // rangeTo.forEach
                } // if
            }
        }
    } // readLines.let

    println( sum )
} // fun main()