import java.io.File

fun main() {
    val radix2 = 2

    val padZero = '0'

    val comma = ","
    val oneOrMore = "[.]+"
    val pathname = "src/Day12.txt"
    val space = " "
    val zeroOrMore = "[.]*"

    val conditions = arrayOf( ".", "#" )

    var sum = 0

    File( pathname ).forEachLine { line ->
        val recordData = line.split( space )

        val groupCounts = recordData[ 1 ].split( comma ).map { sCount ->
            sCount.toInt()
        } // split.map

        val rexBroken = ( zeroOrMore + groupCounts.joinToString( oneOrMore ) { count ->
            "#".repeat(count)
        } + zeroOrMore ).toRegex()

        val qnPos = "\\?".toRegex().findAll( recordData[ 0 ]).map { matchResult ->
            matchResult.range.first
        }.toList()

        0L.rangeTo( "1".repeat( qnPos.size ).toLong( radix2 )).filter { binaryLong ->
            java.lang.Long.toBinaryString( binaryLong ).count { char ->
                char == '1'
            } + recordData[ 0 ].count { unknownChar ->
                unknownChar == '#'
            } == groupCounts.sum()
        }.forEach { binaryLong ->
            var candidate = recordData[ 0 ]

            java.lang.Long.toBinaryString( binaryLong ).padStart( qnPos.size, padZero ).forEachIndexed { binaryIndex, binaryChar ->
                candidate = candidate.replaceRange(
                    IntRange( qnPos[ binaryIndex ], qnPos[ binaryIndex ]),
                    conditions[ binaryChar.digitToInt()]
                ) // candidate.replaceRange
            } // padStart.forEachIndexed

            if ( rexBroken.matches( candidate )) {
                sum++
            } // if
        } // filter.forEach
    } // File.forEachLine

    println( sum )
} // fun main()