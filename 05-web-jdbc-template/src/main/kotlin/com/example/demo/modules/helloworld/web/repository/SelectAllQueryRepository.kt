package com.example.demo.modules.helloworld.web.repository

import com.example.demo.modules.helloworld.web.model.Customer

interface SelectAllQueryRepository {
    fun perform(): List<Customer>
}
