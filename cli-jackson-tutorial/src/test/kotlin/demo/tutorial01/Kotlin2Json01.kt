package demo.tutorial01

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.junit.Test
import java.text.SimpleDateFormat

import kotlin.test.assertEquals

//
// Serialize(Object -> JSON 文字列)
// https://medium.com/@bau1537/jackson%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%9Fjava%E3%81%A8json%E3%81%AE%E7%9B%B8%E4%BA%92%E5%A4%89%E6%8F%9B-a88dad6d8d54
// https://www.studytrails.com/2016/09/10/jackson-create-json/
//
class Kotlin2Json01 {
    @Test
    fun example() {
        val appearanceOfStage = mapOf(
            "ラジオ" to "InterFM",
            "CM" to "ソニー, NTTドコモ, サントリー, 任天堂",
            "テレビ" to "HERO, NHK",
        )
        val artist = Artist(
            "宇多田ヒカル",
            SimpleDateFormat("yyyy-MM-dd").parse("1983-01-19"),
            appearanceOfStage,
        )
        val album = Album(
            "DEEP RIVER",
            artist,
            listOf("dummy link1", "dummy link2"),
            listOf("FINAL DISTANCE", "traveling", "光", "SAKURAドロップス/Letters"),
        )

        val objectMapper = ObjectMapper()
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)
        objectMapper.dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val actual: String = objectMapper.writeValueAsString(album)
        val expected: String = """
            {
              "title" : "DEEP RIVER",
              "artist" : {
                "name" : "宇多田ヒカル",
                "dateOfBirth" : "1983-01-19",
                "appearanceOnStage" : {
                  "ラジオ" : "InterFM",
                  "CM" : "ソニー, NTTドコモ, サントリー, 任天堂",
                  "テレビ" : "HERO, NHK"
                }
              },
              "links" : [ "dummy link1", "dummy link2" ],
              "songs" : [ "FINAL DISTANCE", "traveling", "光", "SAKURAドロップス/Letters" ]
            }
        """.trimIndent()
        assertEquals(expected, actual)
    }
}