package com.example.demo.modules.helloworld.web.repository

import com.example.demo.modules.helloworld.web.model.Customer
import com.example.demo.modules.helloworld.web.model.CustomerRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
@Suppress("unused")
class SelectAllQueryRepositoryImpl(val jdbcTemplate: JdbcTemplate) : SelectAllQueryRepository {
    val sql = """
        SELECT id, first_name, last_name
        FROM customer;
    """.trimIndent()
    val mapper = CustomerRowMapper()
    override fun perform(): List<Customer> {
        return jdbcTemplate.queryForStream(sql, mapper).toList()
    }
}
