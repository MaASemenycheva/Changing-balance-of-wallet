package com.cbr.ilk.camundapostgreskotlin.cruid.insert

import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

object RowInsertWalletTransferRecord {
    @JvmStatic
    fun main(args: Array<String>) {
        // auto close connection and statement
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                    for(i in 1..5000) {
                    val row =
                        statement.executeUpdate(
                            generateInsert(
                                UUID.fromString(UUID.randomUUID().toString()),
                                UUID.fromString(UUID.randomUUID().toString()),
                                87,
                                UUID.fromString(UUID.randomUUID().toString()),
                                UUID.fromString(UUID.randomUUID().toString())
                        )
                        )
                    // rows affected
                    println(row)
                    }
                }
            }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateInsert(p_sender: UUID, p_receiver: UUID, p_amount: Int, p_series_id: UUID, p_origin: UUID): String {
        return "INSERT INTO wallet_transfer (p_sender, p_receiver, p_amount, p_series_id, p_origin) " +
                "VALUES ('" + p_sender + "','" + p_receiver + "','" + p_amount + "','" + p_series_id + "','"+ p_origin + "')"
    }
}
