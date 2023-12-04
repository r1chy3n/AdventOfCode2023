import java.io.File

fun main() {
    var sum = 0

    File( "src/Day03.txt" ).bufferedReader().use { bufferedReader ->
        // 要判斷數字周圍是否有符號，一次要取得三行資料
        val threeLines = mutableListOf(
            null,
            bufferedReader.readLine(),
            bufferedReader.readLine()
        ) // mutableListOf
        // 整數的正規表示法
        val digitRegex = Regex( "[0-9]+" )
        // 句點字元
        val period = '.'

        // 宣告在迴圈外，以減少每次對記憶體配置的時間
        var indexStart: Int
        var indexEnd: Int

        // 如果中間那行不為 null，則繼續處理
        while ( threeLines[ 1 ] != null ) {
            // 把中間那行中的數字全部抓出來
            digitRegex.findAll( threeLines[ 1 ]!!).forEach { matchResult ->
                indexStart = Math.max( matchResult.range.first - 1, 0 )
                indexEnd = Math.min( matchResult.range.last + 1, threeLines[ 1 ]!!.lastIndex )

                // 檢查三行中，在該數字的周圍，是否有符號的存在
                threeLines.any { nullableLine ->
                    nullableLine?.let { line ->
                        line.toCharArray( indexStart, indexEnd + 1 ).any { char ->
                            // 不為數字也不為句點
                            !char.isLetterOrDigit() && char != period
                        } // charArray.any
                    } == true
                }.takeIf { anySymbol ->
                    anySymbol
                }?.let {
                    sum += matchResult.value.toInt()
                } // takeIf?.let
            } // findAll.forEach

            // 處理完，移除第一行
            threeLines.removeFirst()
            // 如果中間那行不為 null，則讀取下一行
            threeLines[ 1 ]?.let {
                threeLines.add( bufferedReader.readLine())
            } // threeLines[1].let
        } // while
    } // bufferedReader.use

    println( sum )
} // fun main