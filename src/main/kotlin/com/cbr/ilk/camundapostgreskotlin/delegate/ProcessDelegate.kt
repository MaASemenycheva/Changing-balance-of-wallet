package com.cbr.ilk.camundapostgreskotlin.delegate

import com.cbr.ilk.camundapostgreskotlin.jdbc.JDBCRecord
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.Variables
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis


@Component
class ProcessDelegate : JavaDelegate {
    private val logger = LoggerFactory.getLogger(ProcessDelegate::class.java)
    private val processEngine: ProcessEngine? = null
    private val PROCESS_KEY = "app-process"//"Process_027hatc"//"app-process" //"Process_027hatc"
    override fun execute(execution: DelegateExecution?) {
        var processes = JDBCRecord ()
        val processesList = processes.startProcesses(100)
        processesList.forEach { process ->
            logger.info("New_handled_process= " +
                    "message_uuid=${process.message_uuid}, " +
                    "created=${process.created} " +
                    "payload=${process.payload} " +
                    "message_type=${process.message_type}")
            val elapsedTime = measureTimeMillis {
                execution!!.processEngineServices.runtimeService.startProcessInstanceByKey(
                    PROCESS_KEY,
//                    execution?.processInstanceId,
                    process.message_type.toString(), //processDefinitionKey
                    process.message_uuid.toString(), //businessKey
                    Variables.createVariables()
                        .putValueTyped("payload", Variables.stringValue(process.payload)) //variables
                )
            }
            logger.info("The code took to execute $elapsedTime ms")
        }
        logger.info("ProcessInstanceId = {}", execution?.processInstanceId)
    }
}