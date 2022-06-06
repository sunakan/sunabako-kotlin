package com.example.demo.modules.helloworld.web.controller

import com.example.demo.modules.helloworld.web.service.SelectAllQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Suppress("unused")
class CustomerController(val selectAllQueryService: SelectAllQueryService) {
    //
    // customers テーブルから全て取得
    //
    // curl --location --request GET 'http://localhost:8080/customers' | jq '.'
    //
    @GetMapping("/customers")
    fun select(): CustomerResponse {
        return CustomerResponse(customers = selectAllQueryService.selectAllQuery())
    }
}