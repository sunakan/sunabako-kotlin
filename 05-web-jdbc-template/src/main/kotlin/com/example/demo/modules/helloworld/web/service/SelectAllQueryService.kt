package com.example.demo.modules.helloworld.web.service

import com.example.demo.modules.helloworld.web.model.Customer

interface SelectAllQueryService {
    fun selectAllQuery(): List<Customer>
}