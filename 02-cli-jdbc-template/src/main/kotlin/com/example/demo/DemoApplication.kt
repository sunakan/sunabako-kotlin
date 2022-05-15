package com.example.demo

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class DemoApplication(val jdbcTemplate: JdbcTemplate) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        InsertCommandImpl(jdbcTemplate).perform("John", "Smith")
        println("--------")
        SelectAllQueryImpl(jdbcTemplate).perform().forEach { println(it) }
        println("--------")
        SelectSatoOrSuzukiQueryImpl(jdbcTemplate).perform().forEach { println(it) }
        println("--------")
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
