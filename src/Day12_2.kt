import java.io.File

fun main() {
    val pathname = "src/Day12.txt"
    val space = " "
    val comma = ","
    val one = 1
    val unfold = 0.rangeTo( 4 )
    val qn = "?"

    class ConditionRecord1(
        private val unknownRecord: String,
        private val groupCounts: List<Int>
    ) {
        private val lengthDiff = unknownRecord.length -
                ( groupCounts.sum() + groupCounts.lastIndex )

        private val permutedResults = HashMap<Pair<Int, List<Int>>, Long>()

        fun getPossible(): Long {
            return findMatch(
                Pair( 0, groupCounts ),
                lengthDiff
            )
        }

        fun findMatch( indexCounts: Pair<Int, List<Int>>, allowed: Int ): Long {
            return permutedResults.getOrElse( indexCounts ) {
                var possible = 0L

                if ( indexCounts.second.isEmpty()) {
                    if (
                        unknownRecord.drop( indexCounts.first ).none { unknownChar ->
                            unknownChar == '#'
                        } // drop.none
                    ) {
                        possible++
                    } // if
                } else {
                    val subUnknown = unknownRecord.drop( indexCounts.first )

                    0.rangeTo( allowed ).forEach { d ->
                        var dotCount = d

                        if ( indexCounts.second.size < groupCounts.size ) {
                            dotCount++
                        } // if

                        if (
                            subUnknown.take( dotCount ).none { unknownChar ->
                                unknownChar == '#'
                            } && subUnknown.drop( dotCount ).take( indexCounts.second.first()).none { unknownChar ->
                                unknownChar == '.'
                            } // take.none
                        ) {
                            val newAllowed = allowed - d

                            possible += findMatch(
                                Pair(
                                    indexCounts.first + dotCount + indexCounts.second.first(),
                                    indexCounts.second.drop( one )
                                ),
                                newAllowed
                            )
                        } // if
                    } // IntRange.forEach
                } // if - else

                possible
            }.also { result ->
                permutedResults[ indexCounts ] = result
            } // getOrElse.also
        } // fun getPossible
    } // class ConditionRecord

    var sum = 0L

    File( pathname ).forEachLine { line ->
        val rowData = line.split( space )

        sum += ConditionRecord1(
            unfold.joinToString( qn ) {
                rowData[ 0 ]
            },
            unfold.joinToString( comma ) {
                rowData[ 1 ]
            }.split( comma ).map { countStr ->
                countStr.toInt()
            } // split.map
        ).getPossible()
    } // File.forEachLine

    println( "sum = $sum" )
} // fun main()

