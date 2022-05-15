package com.example.demo

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class CustomerRowMapper : RowMapper<Customer> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Customer {
        return Customer(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
        )
    }
}
