import java.io.File

fun main() {
    val gameIds = mutableListOf<Int>()
    val colorLimits = mapOf( "red" to 12, "green" to 13, "blue" to 14 )

    File( "src/Day02.txt").bufferedReader().forEachLine { line ->
        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        val gameCubes = line.split( ": " )

        if (
            gameCubes[ 1 ].split( "; " ).none { cubeSet ->
                // 3 blue, 4 red
                cubeSet.split( ", " ).any { colorCubes ->
                    // 3 blue
                    val countColor = colorCubes.split( " " )

                    colorLimits[ countColor[ 1 ]]?.let { limit ->
                        limit < countColor[ 0 ].toInt()
                    } == true
                } // split.any
            } // split.none
        ) {
            gameIds.add( gameCubes[ 0 ].split( " " )[ 1 ].toInt())
        } // if
    } // bufferedReader.forEachLine

    println( gameIds.sum())
} // fun main