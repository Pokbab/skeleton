package com.cdg.skeleton.api.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.swagger.v3.core.converter.ModelConverters
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.core.jackson.TypeNameResolver
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Core API")
                    .description("Core API")
                    .version("1.0.0")
            )
    }

    @PostConstruct
    fun init() {
        val innerClassAwareTypeNameResolver = object : TypeNameResolver() {
            override fun getNameOfClass(cls: Class<*>): String {
                return cls.name.substringAfterLast(".").replace("$", "_")
            }
        }
        ModelConverters.getInstance()
            .addConverter(ModelResolver(jacksonObjectMapper(), innerClassAwareTypeNameResolver))
    }
}