import java.io.File

fun main() {
    var lowest = Long.MAX_VALUE
    var seeds = listOf<Long>()

    val dictionaries = mutableListOf<List<Pair<LongRange, Long>>>()

    File( "src/Day05.txt" ).bufferedReader().use { bufferedReader ->
        var lineRead: String?

        val space = " "

        do {
            lineRead = bufferedReader.readLine()?.also { line ->
                // seeds: 79 14 55 13
                if ( line.startsWith( "seeds: " )) {
                    seeds = line.split(": ")[1].split( space ).map( String::toLong ).toList()
                }
                // seed-to-soil map:
                else if ( line.endsWith( " map:" )) {
                    dictionaries.add(
                        mutableListOf<Pair<LongRange, Long>>().also { pairList ->
                            do {
                                lineRead = bufferedReader.readLine()?.also { dataLine ->
                                    if ( dataLine.isNotEmpty()) {
                                        dataLine.split( space ).let { token ->
                                            token[ 1 ].toLong().let { source ->
                                                pairList.add(
                                                    Pair(
                                                        ( source..< source + token[ 2 ].toLong()),
                                                        token[ 0 ].toLong()
                                                    ) // Pair
                                                ) // pairList.add
                                            } // toLong.let
                                        } // split.let
                                    } // if
                                } // readLine?.also
                            } while ( lineRead != null && lineRead!!.isNotEmpty())
                        } // mutableListOf.also
                    ) // dictionaries.add
                } // if - else i f
            } // readLine?.also
        } while ( lineRead != null )
    } // bufferedReader.use

    var look4: Long

    // 每個種子找地點
    seeds.forEach { seed ->
        look4 = seed

        // 每一層的結果為下一層的來源
        dictionaries.forEach { pairList ->
            pairList.find { pair ->
                pair.first.contains( look4 )
            }?.let { pair ->
                look4 += pair.second - pair.first.first
            } // find?.let
        } // dictionaries.forEach

        lowest = lowest.coerceAtMost( look4 )
    } // seeds.forEach

    println( lowest )
} // fun main
