import java.io.File

fun main() {
    var totalScratches = 0
    // 卡片編號 to 卡片數
    val cardCounts = mutableMapOf<Int, Int>()

    File( "src/Day04.txt" ).bufferedReader().forEachLine { line ->
        // 本身卡片加一
        totalScratches++

        // 先用垂直線把字串分成兩段
        val cardInfo = line.split( " | " )
        // 用冒號把左半邊分成卡片編號與開獎號碼
        val cardWinnings = cardInfo[ 0 ].split( ": +".toRegex())
        val oneOrMoreSpace = " +".toRegex()
        // 卡片編號
        val cardNumb = cardWinnings[ 0 ].split( oneOrMoreSpace )[ 1 ].toInt()
        // 開獎號碼
        val winnings = cardWinnings[ 1 ].split( oneOrMoreSpace )

        // 前面卡片中獎張數
        totalScratches += cardCounts[ cardNumb ]?: 0

        // 你的號碼
        cardInfo[ 1 ].split( oneOrMoreSpace ).count { numbHave ->
            winnings.contains( numbHave )
        }.takeIf { count ->
            count > 0
        }?.let { count ->
            ( cardNumb + 1..cardNumb + count ).forEach { wonCard ->
                cardCounts[ wonCard ] = ( cardCounts[ wonCard ]?: 0 ) + ( cardCounts[ cardNumb ]?: 0 ) + 1
            } // forEach
        } // let
    } // bufferedReader.forEachLine

    println( totalScratches )
}