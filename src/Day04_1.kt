import java.io.File
import kotlin.math.pow

fun main() {
    val oneOrMoreSpace = " +".toRegex()

    var sum = 0
    var cardInfo: List<String>
    var cardWinnings: List<String>
    var winnings: List<String>

    File( "src/Day04.txt" ).forEachLine { line ->
        // Card 1: 41 48 83 86 17
        // 83 86  6 31 17  9 48 53
        cardInfo = line.split( " | " )
        // Card 1
        // 41 48 83 86 17
        cardWinnings = cardInfo[ 0 ].split( ": +".toRegex())
        winnings = cardWinnings[ 1 ].split( oneOrMoreSpace )

        cardInfo[ 1 ].split( oneOrMoreSpace ).count { numb ->
            winnings.contains( numb )
        }.takeIf { count ->
            count > 0
        }?.let { count ->
            sum += 2.0.pow( count - 1 ).toInt()
        } // takeIf?.let
    } // forEachLine

    println( sum )
}