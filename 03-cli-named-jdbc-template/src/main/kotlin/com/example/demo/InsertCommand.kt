package com.example.demo

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

interface InsertCommand {
    fun perform(firstName: String, lastName: String)
}

@Component
class InsertCommandImpl(val jdbcTemplate: NamedParameterJdbcTemplate) : InsertCommand {
    val sql = "INSERT INTO customer(first_name, last_name) VALUES (:first_name, :last_name);"
    val params = MapSqlParameterSource()
        .addValue("first_name", "Jhon")
        .addValue("last_name", "Smith")
    override fun perform(firstName: String, lastName: String) {
        jdbcTemplate.update(sql, params)
    }
}
