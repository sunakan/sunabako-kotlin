package com.example.demo

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

interface SelectAllQuery {
    fun perform(): List<Customer>
}

@Component
class SelectAllQueryImpl(val jdbcTemplate: NamedParameterJdbcTemplate) : SelectAllQuery {
    val sql = """
        SELECT id, first_name, last_name
        FROM customer;
    """.trimIndent()
    val mapper = CustomerRowMapper()
    val param = MapSqlParameterSource()
    override fun perform(): List<Customer> {
        return jdbcTemplate.queryForStream(sql, param, mapper).toList()
    }
}
