package com.example.demo

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

interface SelectSatoOrSuzukiQuery {
    fun perform(): List<Customer>
}

@Component
class SelectSatoOrSuzukiQueryImpl(val jdbcTemplate: JdbcTemplate) : SelectSatoOrSuzukiQuery {
    val sql = """
        SELECT id, first_name, last_name
        FROM customer
        WHERE first_name IN (?, ?);
    """.trimIndent()
    val mapper = CustomerRowMapper()
    val firstName1 = "鈴木"
    val firstName2 = "佐藤"

    override fun perform(): List<Customer> {
        return jdbcTemplate.queryForStream(sql, mapper, firstName1, firstName2).toList()
    }
}
