package com.cdg.skeleton.api.config.circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Configuration
class ApiCircuitBreakerConfig(
    private val circuitBreakerRegistryProvider: ObjectProvider<CircuitBreakerRegistry>,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val scheduledExecutor = Executors.newSingleThreadScheduledExecutor()

    @PostConstruct
    fun setup() {
        scheduledExecutor.scheduleAtFixedRate({ clearCircuitBreakers() }, 0, 1, TimeUnit.HOURS)
    }

    private fun clearCircuitBreakers() {
        val circuitBreakerRegistry = circuitBreakerRegistryProvider.getObject()
        val breakers =
            circuitBreakerRegistry.allCircuitBreakers
                .filter { it.state == CircuitBreaker.State.CLOSED }
        breakers.forEach { circuitBreakerRegistry.remove(it.name) }
        log.info("CircuitBreakers cleared: {}", breakers.map { it.name })
    }

    @Bean
    fun circuitBreakerConfig(): CircuitBreakerConfig {
        return CircuitBreakerConfig.custom()
            .failureRateThreshold(50f)
            .waitDurationInOpenState(Duration.ofSeconds(60))
            .permittedNumberOfCallsInHalfOpenState(3)
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(10)
            .minimumNumberOfCalls(5)
            .build()
    }

    @Bean
    fun circuitBreakerRegistry(circuitBreakerConfig: CircuitBreakerConfig): CircuitBreakerRegistry {
        return CircuitBreakerRegistry.of(circuitBreakerConfig)
    }
}
