package com.example.demo.controller

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

interface InsertCommand {
    fun perform(firstName: String, lastName: String)
}

@Component
class InsertCommandImpl(val jdbcTemplate: JdbcTemplate) : InsertCommand {
    val sql = "Insert INTO customer (first_name, last_name) VALUES (?, ?)"
    override fun perform(firstName: String, lastName: String) {
        jdbcTemplate.update(sql, firstName, lastName)
    }
}