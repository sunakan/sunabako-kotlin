package com.example.demo.controller

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("customers")
@Suppress("unused")
class CustomerController(val jdbcTemplate: JdbcTemplate) {
    @GetMapping("")
    fun getAllCustomers(): List<Customer> {
        return SelectAllQueryImpl(jdbcTemplate).perform()
    }

    @GetMapping("/SatoOrTanaka")
    fun getSatoOrTanaka(): List<Customer>{
        return SelectSatoOrSuzukiQueryImpl(jdbcTemplate).perform()
    }
}