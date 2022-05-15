package com.example.demo

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

interface SelectSatoOrSuzukiQuery {
    fun perform(): List<Customer>
}

@Component
class SelectSatoOrSuzukiQueryImpl(val jdbcTemplate: NamedParameterJdbcTemplate) : SelectSatoOrSuzukiQuery {
    val sql = """
        SELECT id, first_name, last_name
        FROM customer
        WHERE first_name IN (:first_name1, :first_name2);
    """.trimIndent()
    val mapper = CustomerRowMapper()
    val params = MapSqlParameterSource()
        .addValue("first_name1", "鈴木")
        .addValue("first_name2", "佐藤")

    override fun perform(): List<Customer> {
        return jdbcTemplate.queryForStream(sql, params, mapper).toList()
    }
}
