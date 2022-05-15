package com.example.demo

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

interface SelectAllQuery {
    fun perform(): List<Customer>
}

@Component
class SelectAllQueryImpl(val jdbcTemplate: JdbcTemplate) : SelectAllQuery {
    val sql = """
        SELECT id, first_name, last_name
        FROM customer;
    """.trimIndent()
    val mapper = CustomerRowMapper()
    override fun perform(): List<Customer> {
        return jdbcTemplate.queryForStream(sql, mapper).toList()
    }
}
