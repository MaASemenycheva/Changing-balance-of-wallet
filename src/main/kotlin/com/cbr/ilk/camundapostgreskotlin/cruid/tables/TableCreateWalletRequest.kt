package com.cbr.ilk.camundapostgreskotlin.cruid.tables

import java.sql.DriverManager
import java.sql.SQLException


object TableCreateWalletRequest {
    private const val SQL_CREATE = ("CREATE TABLE wallet"
            + "("
            + " origin uuid,"
            + " name text,"
            + " member_id uuid,"
            + " type_id int,"
            + " status_id int,"
            + " balance bigint,"
            + " PRIMARY KEY (origin)"
            + ")")

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->

                    // if DDL failed, it will raise an SQLException
                    statement.execute(SQL_CREATE)
                }
            }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}