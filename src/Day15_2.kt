import java.io.File

fun main() {
    val pathname = "src/Day15.txt"
    val empty = ""
    val comma = ","
    val steps = File( pathname )
        .readLines().joinToString( empty ).split( comma )
    val equal = '='
    val boxes = mutableMapOf<Int, MutableMap<String, Int>>()
    val dash = '-'

    val getHash: (String) -> Int = { label ->
        var hash = 0

        label.forEach { char ->
            hash += char.code
            hash *= 17
            hash %= 256
        } // forEach

        hash
    } // fun hash

    var sum = 0L

    var label: String
    var focal: Int
    var hash: Int
    var box: MutableMap<String, Int>

    steps.forEach { step ->
        if ( step.contains( equal )) {
            step.split( equal ).let {
                label = it[ 0 ]
                focal = it[ 1 ].toInt()
            } // let

            hash = getHash( label )

            ( if ( boxes.containsKey( hash )) boxes.getValue( hash )
            else mutableMapOf<String, Int>().also {
                boxes[ hash ] = it
            })[ label ] = focal
        } else {
            label = step.split( dash )[ 0 ]
            hash = getHash( label )

            if ( boxes.containsKey( hash )) {
                box = boxes.getValue( hash )

                box.remove( label )

                if ( box.isEmpty()) {
                    boxes.remove( hash )
                } // if box.isEmpty
            } // if boxes.containsKey
        } // if - else
    } // oneLine.forEach

    boxes.entries.forEach {
        it.value.entries.forEachIndexed { index, len ->
            sum += ( it.key + 1 ) * ( index + 1 ) * len.value
        } // forEachIndexed
    } // forEach

    println( "sum = $sum")
} // fun main