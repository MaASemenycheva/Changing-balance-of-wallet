package com.cbr.ilk.camundapostgreskotlin.cruid.tables

import java.sql.DriverManager
import java.sql.SQLException


object TableCreateWalletTransferRequest {
    private const val SQL_CREATE = ("CREATE TABLE wallet_transfer"
            + "("
            + " p_sender uuid,"
            + " p_receiver uuid,"
            + " p_amount bigint,"
            + " p_series_id uuid,"
            + " p_origin uuid,"
            + " PRIMARY KEY (p_sender)"
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