import java.io.File
import kotlin.math.abs

fun main() {
    var sum = 0

    File( "src/Day11.txt" ).readLines().toMutableList().let { image ->
        val period = '.'

        // 先處理橫向擴張，也就是垂直都是句號
        image[ 0 ].lastIndex.downTo( 0 ).forEach { charIndex ->
            if (
                image.map { line ->
                    line[ charIndex ]
                }.all { char ->
                    char == period
                }
            ) {
                image.forEachIndexed { index, line ->
                    image[ index ] = line.replaceRange( charIndex, charIndex + 1, ".." )
                } // List.forEach
            } // if
        } // downTo.forEach

        // 再處理直向擴張，也就是一列都是句號
        image.lastIndex.downTo( 0 ).forEach { i ->
            if (
                image[ i ].all { char ->
                    char == period
                } // CharSequence.all
            ) {
                image.add( i, image[ i ])
            } // if
        } // downTo.forEach

        val pound = '#'

        mutableListOf<Pair<Int, Int>>().also { pounds ->
            // 找出所有 # 的座標
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
                        sum += abs( pounds[ otherIndex ].first - pair.first ) +
                                abs( pounds[ otherIndex ].second - pair.second )
                    } // rangeTo.forEach
                } // if
            } // pounds.forEachIndexed
        } // also.let
    } // readLines.let

    println( sum )
} // fun main