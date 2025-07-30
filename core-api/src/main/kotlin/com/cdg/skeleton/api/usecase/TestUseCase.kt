package com.cdg.skeleton.api.usecase

import com.cdg.skeleton.api.config.cache.CacheKey
import com.cdg.skeleton.api.config.circuitbreaker.CircuitBreakerProvider
import com.cdg.skeleton.domain.sample.SampleCriteria
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class TestUseCase(
    private val circuitBreakerProvider: CircuitBreakerProvider,
    private val cacheSupport: CacheSupport,
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val objectMapper =
        jacksonObjectMapper()
            .registerModules(JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun test(
        criteria: SampleCriteria,
        useCache: Boolean,
    ): String {
        try {
            return circuitBreakerProvider.getCircuitBreaker("test").executeCallable {
                if (useCache) {
                    val cacheKey = sha256(objectMapper.writeValueAsString(criteria))
                    cacheSupport.sample(cacheKey, criteria)
                } else {
                    "Hello World"
                }
            }
        } catch (e: CallNotPermittedException) {
            log.error("Circuit is open", e)
            throw e
        }
    }

    private fun sha256(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    @Service
    class CacheSupport {
        @Cacheable(
            key = "sample",
            cacheNames = [CacheKey.MultiLayer.CACHE_NAME_SAMPLE],
            cacheManager = CacheKey.MultiLayer.CACHE_MANAGER_NAME,
        )
        fun sample(
            cacheKey: String,
            criteria: SampleCriteria,
        ): String {
            return "Hello World"
        }
    }
}
