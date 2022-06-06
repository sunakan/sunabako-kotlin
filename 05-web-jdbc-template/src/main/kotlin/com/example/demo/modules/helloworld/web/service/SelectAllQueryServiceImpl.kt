package com.example.demo.modules.helloworld.web.service

import com.example.demo.modules.helloworld.web.model.Customer
import com.example.demo.modules.helloworld.web.repository.SelectAllQueryRepository
import org.springframework.stereotype.Service

@Service
@Suppress("unused")
class SelectAllQueryServiceImpl(val selectAllQueryRepository: SelectAllQueryRepository) : SelectAllQueryService {
    override fun selectAllQuery(): List<Customer> {
        return selectAllQueryRepository.perform()
    }
}