package com.cbr.ilk.camundapostgreskotlin.cruid.select

import com.cbr.ilk.camundapostgreskotlin.model.Record
import com.cbr.ilk.camundapostgreskotlin.model.Wallet
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp
import java.util.*


//class RowSelectWalletRecord {
//}

fun selectWalletRecords(batchSize: Int): MutableList<Wallet> {
    val result: MutableList<Wallet> = ArrayList<Wallet>()
    val sql = "select * from wallet limit $batchSize"
    try {
        DriverManager.getConnection(
            "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
        ).use { conn ->
            conn.createStatement().use { statement ->
                val resultSet = statement.executeQuery(sql)
                while (resultSet.next()) {
                    val origin = resultSet.getString("origin")
                    val name = resultSet.getString("name")
                    val member_id = resultSet.getString("member_id")
                    val type_id = resultSet.getInt("type_id")
                    val status_id = resultSet.getInt("status_id")
                    val balance = resultSet.getInt("balance")
                    val obj = Wallet()
                    obj.origin =  UUID.fromString(origin)
                    obj.name = name
                    obj.member_id = UUID.fromString(member_id)
                    obj.type_id = type_id
                    obj.status_id = status_id
                    obj.balance = balance
                    result.add(obj)
//                    System.out.println(obj)
                }
            }
        }
    } catch (e: SQLException) {
        System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return result
}

object RowSelectWalletRecord {
    @JvmStatic
    fun main(args: Array<String>) {
        selectWalletRecords(10)
    }
}