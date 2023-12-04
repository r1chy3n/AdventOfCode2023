import java.io.File
import java.util.*

fun main() {
    var sum = 0

    File( "src/Day01.txt" ).bufferedReader().forEachLine { line ->
        val charStack = Stack<Char>()

        line.toCharArray().forEach { char ->
            if ( char.isDigit()) {
                charStack.push( char )
            } // if char.isDigit
        } // charArray.forEach

        if ( charStack.isNotEmpty()) {
            sum += "${charStack.first()}${charStack.peek()}".toInt()
        } // if
    } // bufferedReader.forEachLine

    println( sum )
} // fun main()
