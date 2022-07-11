package com.cbr.ilk.camundapostgreskotlin

import com.cbr.ilk.camundapostgreskotlin.camundaTests.ProcessConstants
import org.apache.ibatis.logging.LogFactory
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.ProcessEngineRule
import org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.PostConstruct
/**
 * Test case starting an in-memory database-backed Process Engine.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class InMemoryH2ProcessUnitTest {
    @Autowired
    private val processEngine: ProcessEngine? = null
    @PostConstruct
    fun initRule() {
        rule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build()
    }

    @Before
    fun setup() {
        AbstractAssertions.init(processEngine)
    }

    @Test
    @Deployment(resources = ["empty.bpmn"]) // only required for process test coverage
    fun testStartProcesses() {
        // Drive the process by API and assert correct behavior by camunda-bpm-assert
        val processInstance = AbstractAssertions.processEngine().runtimeService
            .startProcessInstanceByKey(ProcessConstants.PROCESS_DEFINITION_KEY)
        BpmnAwareAssertions.assertThat(processInstance).isEnded
    }

    companion object {
        init {
            LogFactory.useSlf4jLogging() // MyBatis
        }

        @ClassRule
        @Rule
        var rule: ProcessEngineRule? = null
    }
}