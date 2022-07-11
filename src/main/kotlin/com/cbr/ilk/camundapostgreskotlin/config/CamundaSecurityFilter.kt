package com.cbr.ilk.camundapostgreskotlin

import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.Filter


@Configuration
class CamundaSecurityFilter {
    @Bean
    fun processEngineAuthenticationFilter(): FilterRegistrationBean<Filter> {
        val registration = FilterRegistrationBean<Filter>()
        registration.setName("camunda-auth")
        registration.filter = processEngineAuthenticationFilter
        registration.addInitParameter(
            "authentication-provider",
            "org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider"
        )
        registration.addUrlPatterns("/*")
        registration.order = 1
        return registration
    }

    @get:Bean
    val processEngineAuthenticationFilter: Filter
        get() = ProcessEngineAuthenticationFilter()
}