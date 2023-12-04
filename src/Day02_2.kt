import java.io.File

fun main() {
    var sum = 0

    File( "src/Day02.txt" ).bufferedReader().forEachLine { line ->
        // 顏色的最大值
        val colorUppers = mutableMapOf( "red" to 0, "green" to 0, "blue" to 0 )

        // 以 ": " （冒號+空白）將字串對分，取右邊之每次取出的結果組
        // "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        line.split( ": " )[ 1 ]
            // 再以 "; " （分號+空白）將每次取出的結果組分開為各組結果
            // "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
            .split( "; " )
            // 對各組結果進行處理
            // "3 blue, 4 red", "1 red, 2 green, 6 blue", "2 green"
            .forEach { set ->
                // 以 ", " （逗號+空白）將單一結果組分為單一顏色與數量
                // "3 blue, 4 red"
                set.split( ", " )
                    // 對單一顏色與數量進行處理與判斷
                    // "3 blue", "4 red"
                    .forEach { singleColor ->
                        // 以 " " （空白）將單一顏色與數量分為數量與顏色
                        // "3 blue"
                        val cubeInfo = singleColor.split( " " )

                        // 檢查並更新顏色上限
                        colorUppers[ cubeInfo[ 1 ]]?.takeIf { upper ->
                            upper < cubeInfo[ 0 ].toInt()
                        }?.let {
                            colorUppers.replace( cubeInfo[ 1 ], cubeInfo[ 0 ].toInt())
                        } // takeIf?.let
                    } // split", ".forEach
            } // split"; ".forEach

        // 每個顏色的最小上限值相乘積
        var power = 1

        colorUppers.keys.forEach { key ->
            power *= colorUppers.getValue( key )
        } // keys.forEach

        // 加總
        sum += power
    } // bufferedReader.forEachLine

    println( sum )
} // fun main