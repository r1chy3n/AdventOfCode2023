@file:Suppress("LocalVariableName")

import java.io.File

fun main() {
    var steps = 0
    var instructions = ""
    var charPos = 0
    var currentNode = "AAA"

    val R = 'R'
    val L = 'L'
    val finalNode = "ZZZ"
    val nodeMap = mutableMapOf<String, Map<Char, String>>()

    File( "src/Day08.txt" ).readLines().forEachIndexed { index, line ->
        if ( index == 0 )
            instructions = line
        else if ( line.isNotEmpty())
            nodeMap[ line.substring( 0, 3 )] = mapOf(
                L to line.substring( 7, 10 ),
                R to line.substring( 12, 15 )
            )
    } // readLines.forEachIndexed

    while ( currentNode != finalNode ) {
        currentNode = nodeMap.getValue( currentNode ).getValue( instructions[ charPos++ ])

        steps++

        if ( charPos == instructions.length )
            charPos = 0
    } // while

    println( steps )
} // fun main()