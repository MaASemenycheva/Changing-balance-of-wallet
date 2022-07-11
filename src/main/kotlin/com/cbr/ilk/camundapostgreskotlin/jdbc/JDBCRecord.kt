package com.cbr.ilk.camundapostgreskotlin.jdbc

import com.cbr.ilk.camundapostgreskotlin.model.Record
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.*
import java.util.*
import java.util.Date
import javax.sql.DataSource

class JDBCRecord {

    fun startProcesses(batchSize: Int): MutableList<Record> {
        val result: MutableList<Record> = ArrayList<Record>()
        var retryCount = 5
        var transactionCompleted = false
        do {
            try {
                DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
                ).use { conn ->
                    conn.autoCommit = false;
                    conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                    ).use { preparedStatement ->
                        val resultSet =
                            preparedStatement.executeQuery("select * from request where processed is null limit $batchSize for no key update skip locked")
                        while (resultSet.next()) {
                            val message_uuid = UUID.fromString(resultSet.getString("message_uuid"))
                            val payload = resultSet.getString("payload")
                            val created = resultSet.getTimestamp("created")
                            val message_type = resultSet.getInt("message_type")
                            val obj = Record()
                            obj.message_uuid = message_uuid
                            obj.payload = payload
                            // Timestamp -> LocalDateTime
                            obj.created = created.toLocalDateTime()
                            obj.message_type = message_type
                            resultSet.updateTimestamp("processed", Timestamp(Date().time))
                            resultSet.updateRow();
                            result.add(obj)
                            conn.commit();
                        }
                        resultSet?.close()
                        transactionCompleted = true
                    }
                    if (conn != null) {
                        try {
                            // If we got here, and conn is not null, the
                            // transaction should be rolled back, as not
                            // all work has been done
                            conn.use { conn ->
                                conn.rollback();
                            }
                        } catch (sqlEx: SQLException) {
                            // If we got an exception here, something
                            // pretty serious is going on, so we better
                            // pass it up the stack, rather than just
                            // logging it. . .
                            throw sqlEx;
                        }
                    }

                }
            } catch (sqlEx: SQLException) {
                // The two SQL states that are 'retry-able'
                // for a communications error.
                // Only retry if the error was due to a stale connection,
                // communications problem
                val sqlState = sqlEx.sqlState
                if ("Substitute with Your DB documented sqlstate number for stale connection" == sqlState) {
                    retryCount--;
                } else {
                    retryCount = 0;
                }
            }
        } while (!transactionCompleted && (retryCount > 0));
        return result
    }
}