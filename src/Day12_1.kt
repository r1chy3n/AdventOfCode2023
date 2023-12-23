import java.io.File

@Suppress("SpellCheckingInspection")
fun main() {
    val qMark = '?'
    val zeroOrMore = "[.]*"
    val conditions = arrayOf( ".", "#" )

    var sum = 0

    File( "src/Day12.txt" ).forEachLine { line ->
        // "???.### 1,1,3"
        // => [0]="???.###"
        //    [1]="1,1,3"
        line.split( " " ).let { records ->
            // [1]="1,1,3"
            records[ 1 ].split( "," ).map { str ->
                // [0]="#"
                // [1]="#"
                // [2]="###"
                "#".repeat( str.toInt())
            }.toMutableList().let { mutableBrokens ->
                // [0]="[.]*"
                // [1]="#"
                // [2]="[.]+"
                // [3]="#"
                // [4]="[.]+"
                // [5]="###"
                // [6]="[.]*"
                mutableBrokens.lastIndex.downTo( 1 ).forEach { index ->
                    mutableBrokens.add( index, "[.]+" )
                } // IntProgress.forEach

                mutableBrokens.add( 0, zeroOrMore )
                mutableBrokens.add( zeroOrMore )
                // "[.]*#[.]+#[.]+###[.]*"
                mutableBrokens.joinToString( "" ).toRegex()
            }.let { brokenRegex ->
                // [0]="???.###"
                "\\?".toRegex().findAll( records[ 0 ]).map { matchR ->
                    // 找出 ? 所在位置
                    matchR.range.first
                }.toList().let { qMarkPos ->
                    records[ 0 ].count { char ->
                        char == qMark
                    }.let { qMarkCount ->
                        0.rangeTo( "1".repeat( qMarkCount ).toInt( 2 )).forEach { binInt ->
                            // "000" -> "111" >>> "..." -> "###"
                            var candidate = records[ 0 ]

                            Integer.toBinaryString( binInt ).padStart( qMarkCount, '0' ).forEachIndexed { index, char ->
                                candidate = candidate.replaceRange( qMarkPos[ index ], qMarkPos[ index ] + 1, conditions[ char.digitToInt()])
                            }

                            if ( brokenRegex.matches( candidate )) {
                                sum++
                            } // if
                        } // IntRange.forEach
                    } // count.let
                } // toList.let
            } // let.let
        } // line.split.let
    } // File.forEachLine

    println( sum )
} // fun main()