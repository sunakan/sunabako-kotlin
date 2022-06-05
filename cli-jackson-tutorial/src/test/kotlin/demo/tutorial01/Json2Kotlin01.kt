package demo.tutorial01

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.junit.Test
import java.text.SimpleDateFormat


//
// Deserialize(JSON 文字列 -> Object)
// https://medium.com/@bau1537/jackson%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%9Fjava%E3%81%A8json%E3%81%AE%E7%9B%B8%E4%BA%92%E5%A4%89%E6%8F%9B-a88dad6d8d54
// https://www.studytrails.com/2016/09/10/jackson-create-json/
//
//
// $ ./gradlew test
//     {}
//    --------1
//     {"Album-Title":"Kind Of Blue"}
//    --------2
//     {"Album-Title":"Kind Of Blue","links":["link1","link2"]}
//    --------3
//     {"Album-Title":"Kind Of Blue","links":["link1","link2"],"artist":{"Artist-Name":"Miles Davis","birthDate":"26 May 1926"}}
//    --------4
//     {"Album-Title":"Kind Of Blue","links":["link1","link2"],"artist":{"Artist-Name":"Miles Davis","birthDate":"26 May 1926"},"musicians":{"Julian Adderley":"Alto Saxophone","Miles Davis":"Trumpet, Band leader"}}
//
class Json2Kotlin01 {
    @Test
    fun example() {
        //val factory = JsonNodeFactory(false)
        //val jsonFactory = JsonFactory()
        //val generator = jsonFactory.createGenerator(System.out)
        //val mapper = ObjectMapper()
        //val album = factory.objectNode()

        //mapper.writeTree(generator, album)
        //println("--------1")
        //album.put("Album-Title", "Kind Of Blue");
        //mapper.writeTree(generator, album)
        //println("--------2")
        //val links: ArrayNode = factory.arrayNode()
        //links.add("link1").add("link2")
        //album.put("links", links)
        //mapper.writeTree(generator, album)
        //println("--------3")
        //val artist = factory.objectNode()
        //artist.put("Artist-Name", "Miles Davis")
        //artist.put("birthDate", "26 May 1926")
        //album.put("artist", artist) // ObjectNodeを第2引数で取るputはDeprecated
        //mapper.writeTree(generator, album)
        //println("--------4")
        //val musicians = factory.objectNode()
        //musicians.put("Julian Adderley", "Alto Saxophone")
        //musicians.put("Miles Davis", "Trumpet, Band leader")
        //album.put("musicians", musicians) // ObjectNodeを第2引数で取るputはDeprecated
        //mapper.writeTree(generator, album)
    }
}