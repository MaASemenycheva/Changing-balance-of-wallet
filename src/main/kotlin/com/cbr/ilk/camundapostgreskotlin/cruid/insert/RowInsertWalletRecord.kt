package com.cbr.ilk.camundapostgreskotlin.cruid.insert

import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

object RowInsertWalletRecord {
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
                                "String".toString(),
                                UUID.fromString(UUID.randomUUID().toString()),
                                0,
                                667,
                                55555555

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


//    + " origin uuid,"
//    + " name text,"
//    + " member_id uuid,"
//    + " type_id int,"
//    + " status_id int,"
//    + " balance bigint,"
//    + " PRIMARY KEY (origin)"

    private fun generateInsert(origin: UUID, name: String, member_id: UUID, type_id: Int, status_id: Int, balance: Int): String {
//        return "INSERT INTO wallet (origin, name, member_id, type_id, status_id, balance) " +
//                "VALUES ('" + origin + "','" + name + "','" + member_id + "','" + type_id + "','" + status_id + "','" +balance + "')"
        return "INSERT INTO wallet (origin, name, member_id, type_id, status_id, balance) " +
                "VALUES ('" + origin + "','" + name + "','" + member_id + "','" + type_id + "','" + status_id + "','" +balance + "')"
    }
}
