package demo.tutorial02

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import kotlin.test.assertEquals

//
// Serialize(Object -> JSON 文字列)
// https://stackabuse.com/reading-and-writing-json-in-kotlin-with-jackson/
//
// 相互参照の関係にある Author と Book
// この時のto JSON
//
//
class Kotlin2Json02 {
    @Test
    fun example() {
        val author = Author("JK Rowling", mutableListOf())
        val bookOne = Book("Harry Potter 1", author)
        val bookTwo = Book("Harry Potter 2", author)
        author.books.add(bookOne)
        author.books.add(bookTwo)

        val mapper = jacksonObjectMapper()
        val actual = mapper.writeValueAsString(author)
        val expected = "{\"name\":\"JK Rowling\",\"books\":[{\"title\":\"Harry Potter 1\"},{\"title\":\"Harry Potter 2\"}]}"
        assertEquals(expected, actual)
    }
}