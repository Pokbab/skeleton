package com.cdg.skeleton.api.config.cache

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.retry.Retry
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Supplier

class CircuitBreakerCacheManager(
    private val delegate: CacheManager,
    private val cacheMap: MutableMap<String, Cache> = ConcurrentHashMap(),
    private val circuitBreaker: CircuitBreaker,
    private val silentOnCacheGetFailure: Boolean = true,
    private val silentOnCachePutFailure: Boolean = false,
    private val silentOnCacheEvictFailure: Boolean = false,
    private val silentOnCacheClearFailure: Boolean = false,
    private val retryPolicyProvider: Supplier<Retry>? = null,
) : CacheManager {
    override fun getCache(name: String): Cache? {
        val delegateCache = delegate.getCache(name) ?: return null
        return cacheMap.computeIfAbsent(name) {
            CircuitBreakerCache(
                delegateCache,
                circuitBreaker,
                silentOnCacheGetFailure,
                silentOnCachePutFailure,
                silentOnCacheEvictFailure,
                silentOnCacheClearFailure,
                retryPolicyProvider,
            )
        }
    }

    override fun getCacheNames(): MutableCollection<String> {
        return delegate.cacheNames
    }
}
