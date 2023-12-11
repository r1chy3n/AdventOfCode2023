@file:Suppress("LocalVariableName")

import java.io.File

fun main() {
    val R = 'R'
    val L = 'L'
    val nodeMap = mutableMapOf<String, Map<Char, String>>()

    var instructions = ""

    File( "src/Day08.txt" ).readLines().forEachIndexed { index, line ->
        if ( index == 0 )
            instructions = line
        else if ( line.isNotEmpty())
            nodeMap[ line.substring( 0, 3 )] = mapOf(
                L to line.substring( 7, 10 ),
                R to line.substring( 12, 15 )
            ) // mapOf
    } // readLines.forEachIndexed

    nodeMap.filter { nodeLR ->
        nodeLR.key.endsWith( 'A' )
    }.keys.map { startNode ->
        var currentNode = startNode
        var charPos = 0
        var step: Long = 0

        while ( !currentNode.endsWith( 'Z' )) {
            currentNode = nodeMap.getValue( currentNode ).getValue( instructions[ charPos++])

            step++

            if ( charPos == instructions.length )
                charPos = 0
        } // while

        step
    }.let { steps ->
        var lcm = steps[ 0 ]

        steps.forEach { step ->
            val bigger = lcm.coerceAtLeast( step )
            val smaller = lcm.coerceAtMost( step )
            var factor = 0

            do {
                lcm = bigger * ++factor
            } while ( lcm % smaller != 0L )
        } // steps.forEach

        println( lcm )
    } // map.let
} // fun main()
