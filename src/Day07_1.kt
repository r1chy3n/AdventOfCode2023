import java.io.File

fun main() {
    var totalWinnings = 0

    val cards = listOf(
        '2', '3', '4', '5', '6', '7', '8', '9',
        'T', 'J', 'Q', 'K', 'A'
    ) // listOf invoke

    File( "src/Day07.txt" ).readLines().map { line ->
        line.substring( 0, 5 ).let { hand ->
            Triple(
                hand,
                line.substring( 6 ).toInt(),
                // 第一張牌的 index
                hand.groupingBy { card ->
                    card
                }.eachCount().let { diffCounts ->
                    when ( diffCounts.size ) {
                        // 五張牌都一樣
                        1 -> HandType.FIVE_OF_A_KIND
                        // 四張牌都一樣+一張不一樣的牌 or 三張牌一樣+二張牌一樣
                        2 -> if ( diffCounts.containsValue( 4 ))
                            HandType.FOUR_OF_A_KIND else HandType.FULL_HOUSE
                        // 三張牌一樣+其它二張不一樣 or 二張牌一樣+二張牌一樣+一張不一樣的牌
                        3 -> if ( diffCounts.containsValue( 3 ))
                            HandType.THREE_OF_A_KIND else HandType.TWO_PAIR
                        // 二張牌一樣+三張不一樣的牌
                        4 -> HandType.ONE_PAIR
                        // 五張牌都不一樣
                        else -> HandType.HIGH_CARD
                    } // when
                } // eachCount.let
            ) // Triple constructor
        } // substring.let
    }.sortedWith { triple1, triple2 ->
        if ( triple1.third.ordinal == triple2.third.ordinal )
            ( 0..triple1.first.lastIndex ).takeWhile { index ->
                cards.indexOf( triple1.first[ index ]) == cards.indexOf( triple2.first[ index ])
            }.let { zeroes ->
                cards.indexOf( triple1.first[ zeroes.size ]) - cards.indexOf( triple2.first[ zeroes.size ])
            }
        else ( triple1.third.ordinal - triple2.third.ordinal )
    }.forEachIndexed { index, triple ->
        totalWinnings += triple.second * ( index + 1 )
    } // forEach

    println( totalWinnings )
} // fun main

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
} // enum class Type
