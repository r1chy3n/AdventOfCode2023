import java.io.File
import java.util.TreeMap

fun main() {
    var sum = 0

    File( "src/Day01.txt" ).bufferedReader().forEachLine { line ->
        val pos2Num = TreeMap<Int, Int>()

        arrayOf(
            "0", "1","2","3","4", "5", "6", "7", "8", "9",
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
        ).forEachIndexed { index, s ->
            line.indexOf( s ).takeIf { indexOfS ->
                indexOfS != -1
            }?.let { indexOfS ->
                pos2Num[ indexOfS ] = index % 10
            } // takeIf?.let

            line.lastIndexOf( s ).takeIf { indexOfS ->
                indexOfS != -1
            }?.let { indexOfS ->
                pos2Num[ indexOfS ] = index % 10
            } // takeIf?.let
        } // stringArray.forEachIndexed

        sum += pos2Num.firstEntry().value * 10 + pos2Num.lastEntry().value
    } // bufferedReader.forEachLine

    println( sum )
} // fun main