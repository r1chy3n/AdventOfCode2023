import java.io.File

// 整數的正規表示法
val regexInteger = Regex( "[0-9]+" )

fun main() {
    var sum = 0

    File( "src/Day03.txt" ).bufferedReader().use { bufferedReader ->
        // 跟 part 1 一樣，一次要處理三行資料
        val threeLines = mutableListOf(
            null,
            bufferedReader.readLine(),
            bufferedReader.readLine()
        ) // mutableListOf
        // 放每行所找出的數字的地方
        val threeIntegers = mutableListOf(
            null,
            findIntegers( threeLines[ 1 ]!!),
            findIntegers( threeLines[ 2 ]!!)
        ) // mutableListOf
        // 星號的正規表示法
        val regexStar = Regex( "\\*" )

        var indexStart: Int
        var indexEnd: Int
        var aroundList: List<MatchResult>

        // 跟 part 1 一樣，中間那行不為 null 則繼續處理
        while ( threeLines[ 1 ] != null ) {
            // 把中間那行的星號抓出來
            regexStar.findAll( threeLines[ 1 ]!!).forEach { starResult ->
                indexStart = ( starResult.range.first - 1 ).coerceAtLeast( 0 )
                indexEnd = ( starResult.range.last + 1 ).coerceAtMost( threeLines[ 1 ]!!.lastIndex )

                // 使用 Set 來確保結果的單一性
                HashSet<MatchResult>().also { aroundSet ->
                    threeIntegers.forEach { nullableList ->
                        // 如果三行找出的整數不為空
                        nullableList?.forEach { integerResult ->
                            // 找出星號周圍的整數
                            (indexStart..indexEnd).forEach { index ->
                                if ( integerResult.range.contains( index )) {
                                    aroundSet.add( integerResult )
                                } // if
                            } // range.contain
                        } // nullableList?.forEach
                    } // threeIntegers.forEach
                }.takeIf { aroundSet ->
                    aroundSet.size == 2
                }?.let { aroundSet ->
                    aroundList = aroundSet.toList()
                    sum += aroundList[ 0 ].value.toInt() * aroundList[ 1 ].value.toInt()
                } // takeIf?.let
            } // findAll.forEach

            // 處理完，移除第一行
            threeLines.removeFirst()
            threeIntegers.removeFirst()
            // 如果中間那行不為 null，則讀取下一行
            threeLines[ 1 ]?.let {
                threeLines.add( bufferedReader.readLine())

                // 如果第三行不為 null，則找出所有數字
                threeLines[ 2 ]?.let { thirdLine ->
                    threeIntegers.add( findIntegers( thirdLine ))
                } // threeLines[2]?.let
            } // threeLines[1]?.let
        } // while
    } // bufferedReader.use

    println( sum )
} // fun main

private fun findIntegers( line: String ) = regexInteger.findAll( line ).toList()
