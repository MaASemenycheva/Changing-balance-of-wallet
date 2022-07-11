package com.cbr.ilk.camundapostgreskotlin.cruid.tables

import java.sql.DriverManager
import java.sql.SQLException


object TableCreateRequest {
    private const val SQL_CREATE = ("CREATE TABLE request"
            + "("
            + " message_uuid uuid,"
            + " payload json,"
            + " created timestamp without time zone DEFAULT CURRENT_TIMESTAMP,"
            + " processed timestamp without time zone,"
            + " message_type integer,"
            + " PRIMARY KEY (message_uuid)"
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