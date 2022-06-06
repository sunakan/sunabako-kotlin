package com.example.demo.modules.helloworld.web.controller

import com.example.demo.modules.helloworld.web.model.Customer

data class CustomerResponse(
    val customers: List<Customer>,
)
