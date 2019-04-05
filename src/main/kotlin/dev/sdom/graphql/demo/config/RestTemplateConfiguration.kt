package dev.sdom.graphql.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfiguration {
        @Bean
        fun restTemplate() = RestTemplate()
}