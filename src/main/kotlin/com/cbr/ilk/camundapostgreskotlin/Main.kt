package com.cbr.ilk.camundapostgreskotlin

import com.cbr.ilk.camundapostgreskotlin.jdbc.JDBCRecord
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.sql.DriverManager
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@EnableProcessApplication
@SpringBootApplication
class Main

fun main(args: Array<String>) {
    var processes = JDBCRecord ()
    val processesList = processes.startProcesses(10)
    processesList.forEach { process ->
            println("Unhandled_process= " +
                    "message_uuid=${process.message_uuid} " +
                    "created=${process.created} " +
                    "payload=${process.payload} " +
                    "message_type=${process.message_type}")
        }
    SpringApplication.run(Main::class.java)
}
