package com.example.demo

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.transaction.TransactionManager
import javax.sql.DataSource

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

// @Configuration
// class Dbconfiguration {
object Dbconfiguration {
    fun dataSource(): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/hoge-db"
        hikariConfig.username = "hoge-user"
        hikariConfig.password = "hoge-pass"
        hikariConfig.connectionTimeout = java.lang.Long.valueOf(500)
        hikariConfig.isAutoCommit = true
        hikariConfig.transactionIsolation = "TRANSACTION_READ_COMMITTED"
        hikariConfig.poolName = "hogePool01"
        hikariConfig.maximumPoolSize = 10
        return HikariDataSource(hikariConfig)
    }
    fun namedParameterJdbcTemplate(dataSource: DataSource = dataSource()): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(dataSource)
    }
    fun transactionManager(dataSource: DataSource = dataSource()): TransactionManager {
        return JdbcTransactionManager(dataSource)
    }
}
