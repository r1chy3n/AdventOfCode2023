import java.io.File

fun main() {
    var lowest = Long.MAX_VALUE

    val seedRanges = mutableListOf<LongRange>()
    val dictionaries = mutableListOf<List<Pair<LongRange, Long>>>()

    File( "src/Day05.txt" ).bufferedReader().use { bufferedReader ->
        var lineRead: String?

        val space = " "

        do {
            lineRead = bufferedReader.readLine()?.also { line ->
                // "seeds: 79 14 55 13"
                if ( line.startsWith( "seeds: " )) {
                    line.split( ": " )[ 1 ].split( space ).let { seeds ->
                        ( 0..seeds.lastIndex step 2 ).forEach { i ->
                            seeds[ i ].toLong().let { rangeStart ->
                                seedRanges.add( LongRange( rangeStart, rangeStart + seeds[ i + 1 ].toLong() - 1 ))
                            } // Long.let
                        } // IntRange.forEach
                    } // List<String>.let
                }
                // "seed-to-soil map:"
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

    var looking4: Long

    // 每個種子找地點
    seedRanges.forEachIndexed { index, seedRange ->
        // let u know the progress
        println( index )
        
        // 79(+14)..92
        seedRange.forEach { seed ->
            // 79
            looking4 = seed

            dictionaries.forEach { pairList ->
                pairList.find { pair ->
                    pair.first.contains( looking4 )
                }?.let { pair ->
                    looking4 += pair.second - pair.first.first
                } // find?.let
            } // dictionaries.forEach

            lowest = lowest.coerceAtMost( looking4 )
        } // seedRange.forEach
    } // seedRanges.forEach

    println( lowest )
} // fun main
