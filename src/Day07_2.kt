import java.io.File

fun main() {
    var totalWinnings = 0

    val cards = listOf(
        'J', '2', '3', '4', '5', '6', '7', '8', '9',
        'T', 'Q', 'K', 'A'
    ) // cards=listOf

    File( "src/Day07.txt" ).readLines().map { line ->
        line.substring( 0, 5 ).let { hand ->
            Triple(
                hand,
                line.substring( 6 ).toInt(),
                hand.filter { card ->
                    card != 'J'
                }.let { noJoker ->
                    if ( noJoker.isEmpty()) HandType.FIVE_OF_A_KIND
                    else noJoker.groupingBy { card ->
                        card
                    }.eachCount().let { cardCounts ->
                        // 找出最大值
                        cardCounts.maxBy { cardCount ->
                            cardCount.value
                        }.let { maxCount ->
                            // 檢查最大值有幾個
                            cardCounts.filter { cardCount ->
                                cardCount.value == maxCount.value
                            }.let { maxCounts ->
                                if ( maxCounts.size == 1 )
                                    hand.replace( 'J', maxCount.key )
                                else maxCounts.maxBy { cardCount ->
                                    cards.indexOf( cardCount.key )
                                }.let { maxCard ->
                                    hand.replace( 'J', maxCard.key )
                                }
                            }.groupingBy { card ->
                                card
                            }.eachCount().let { cardCounts ->
                                when ( cardCounts.size ) {
                                    1 -> HandType.FIVE_OF_A_KIND
                                    2 -> if ( cardCounts.containsValue( 4 ))
                                        HandType.FOUR_OF_A_KIND else HandType.FULL_HOUSE
                                    3 -> if ( cardCounts.containsValue( 3 ))
                                        HandType.THREE_OF_A_KIND else HandType.TWO_PAIR
                                    4 -> HandType.ONE_PAIR
                                    else -> HandType.HIGH_CARD
                                } // when
                            } // eachCount.let
                        } // maxBy.let
                    } // eachCount.let
                } // filter.let
            ) // Triple Constructor
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
    } // sortedWith.forEachIndexed

    println( totalWinnings )
} // fun main()

//private fun