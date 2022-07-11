package com.cbr.ilk.camundapostgreskotlin

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component("logger")
class LoggerDelegate() : JavaDelegate {
    private val LOGGER = Logger.getLogger(LoggerDelegate::class.java.name)

    @Throws(Exception::class)
    override fun execute(execution: DelegateExecution) {
        LOGGER.info(
            "\n\n  ... LoggerDelegate invoked by "
                    + "activtyName='" + execution.currentActivityName + "'"
                    + ", activtyId=" + execution.currentActivityId
                    + ", processDefinitionId=" + execution.processDefinitionId
                    + ", processInstanceId=" + execution.processInstanceId
                    + ", businessKey=" + execution.processBusinessKey
                    + ", executionId=" + execution.id
                    + ", variables=" + execution.variables
                    + " \n\n"
        )
    }
}
