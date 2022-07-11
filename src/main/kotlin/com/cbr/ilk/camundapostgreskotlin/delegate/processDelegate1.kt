package com.cbr.ilk.camundapostgreskotlin.delegate

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.sql.DriverManager
import kotlin.system.measureTimeMillis


//@Component
//class processDelegate1 : JavaDelegate {
//
//    private val logger = LoggerFactory.getLogger(processDelegate1::class.java)
//
//    override fun execute(execution: DelegateExecution?) {
//        DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
//        ).use { conn ->
//            val elapsedTime = measureTimeMillis {
//                conn.createStatement().use { statement ->
//                    val row = statement.executeQuery(
//                        "SELECT 1"
//                    )
//
//                }
//            }
//            logger.info("Time $elapsedTime")
//        }
//    }
//}