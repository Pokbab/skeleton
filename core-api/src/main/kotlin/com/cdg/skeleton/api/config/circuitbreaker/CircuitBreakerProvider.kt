package com.cdg.skeleton.api.config.circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.stereotype.Component

@Component
class CircuitBreakerProvider(
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
) {
    fun getCircuitBreaker(name: String): CircuitBreaker {
        return circuitBreakerRegistry.circuitBreaker(name)
    }
}
