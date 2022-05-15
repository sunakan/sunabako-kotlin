package com.example.demo

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        println(getHello())
    }
    fun getHello(): String = "HelloWorld"
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
