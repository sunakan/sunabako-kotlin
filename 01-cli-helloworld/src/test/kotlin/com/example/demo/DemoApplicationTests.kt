package com.example.demo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DemoApplicationTests {
    @Test fun instanceHasAGetHelloMethod() {
        val instance = DemoApplication()
        assertNotNull(instance.getHello(), "getHelloメソッドを持っている")
    }

    @Test fun getHelloMethodReturnHelloWorldString() {
        val expected = "HelloWorld"
        val actual = DemoApplication().getHello()
        assertEquals(expected, actual, "getHelloメソッドは\"HelloWorld\"を返す")
    }
}
