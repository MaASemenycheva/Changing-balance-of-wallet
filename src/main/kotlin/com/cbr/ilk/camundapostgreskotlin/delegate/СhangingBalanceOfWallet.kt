package com.cbr.ilk.camundapostgreskotlin.delegate

import com.cbr.ilk.camundapostgreskotlin.model.Wallet
import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.Variables
import org.springframework.stereotype.Component
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import kotlin.collections.ArrayList

@Component
internal class СhangingBalanceOfWallet : JavaDelegate {

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
                        System.out.println(obj)
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


    fun insertDataToWalletTransfer(amount: Int) {
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                        val row =
                            statement.executeUpdate(
                                generateInsert(
                                    UUID.fromString(UUID.randomUUID().toString()),
                                    UUID.fromString(UUID.randomUUID().toString()),
                                    amount,
                                    UUID.fromString(UUID.randomUUID().toString()),
                                    UUID.fromString(UUID.randomUUID().toString())
                                )
                            )
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


    @Throws(Exception::class)
    override fun execute(delegateExecution: DelegateExecution) {
        val walletRecord = com.cbr.ilk.camundapostgreskotlin.cruid.select.selectWalletRecords(10)
        for (element in walletRecord) {
            val transferAmount = (Math.random() * 100).toInt()
            val numberOfCoins = (Math.random() * 100).toInt()
            var transferStatus = "Undefined"
            var isTransfer = false

            if (transferAmount < 0) {
                throw BpmnError("transferError")
            }

            if (numberOfCoins - transferAmount >= 0) {
                isTransfer = true
                transferStatus = "Transferred"
            } else {
                transferStatus = "Transfer fail! :( "
            }
            delegateExecution.setVariable("transferAmount", transferAmount)
            delegateExecution.setVariable("numberOfCoins", numberOfCoins)
            delegateExecution.setVariable("transferStatus", transferStatus)
            delegateExecution.setVariable("isTransfer", isTransfer)
            insertDataToWalletTransfer(transferAmount)
        }
    }
}