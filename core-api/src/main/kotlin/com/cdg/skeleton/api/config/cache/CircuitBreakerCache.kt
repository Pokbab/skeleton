package com.cdg.skeleton.api.config.cache

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.retry.Retry
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import java.util.concurrent.Callable
import java.util.function.Supplier

class CircuitBreakerCache(
    private val delegate: Cache,
    private val circuitBreaker: CircuitBreaker,
    private val silentOnCacheGetFailure: Boolean = true,
    private val silentOnCachePutFailure: Boolean = false,
    private val silentOnCacheEvictFailure: Boolean = false,
    private val silentOnCacheClearFailure: Boolean = false,
    private val retryPolicyProvider: Supplier<Retry>? = null,
) : Cache {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun getName(): String {
        return delegate.name
    }

    override fun getNativeCache(): Any {
        return delegate
    }

    override fun get(key: Any): Cache.ValueWrapper? {
        val retry: Retry? = retryPolicyProvider?.get()

        var supplier: Supplier<Cache.ValueWrapper?> = Supplier { delegate.get(key) }
        if (retry != null) {
            supplier = Retry.decorateSupplier(retry, supplier)
        }

        val decoratedSupplier = CircuitBreaker.decorateSupplier<Cache.ValueWrapper?>(circuitBreaker, supplier)

        return if (silentOnCacheGetFailure) {
            runCatching {
                decoratedSupplier.get()
            }.getOrElse {
                log.error("[Cache] Error occurred while getting cached object. fail-over to return null value.", it)
                Cache.ValueWrapper { null }
            }
        } else {
            decoratedSupplier.get()
        }
    }

    override fun <T : Any?> get(
        key: Any,
        type: Class<T>?,
    ): T? {
        val retry: Retry? = retryPolicyProvider?.get()

        var supplier: Supplier<T?> = Supplier { delegate.get(key, type) }
        if (retry != null) {
            supplier = Retry.decorateSupplier(retry, supplier)
        }

        val decoratedSupplier = CircuitBreaker.decorateSupplier<T?>(circuitBreaker, supplier)

        return if (silentOnCacheGetFailure) {
            runCatching {
                decoratedSupplier.get()
            }.onFailure {
                log.error("[Cache] Error occurred while getting cached object. fail-over to return null value.", it)
            }.getOrNull()
        } else {
            decoratedSupplier.get()
        }
    }

    override fun <T : Any?> get(
        key: Any,
        valueLoader: Callable<T>,
    ): T? {
        val retry: Retry? = retryPolicyProvider?.get()

        var supplier: Supplier<T?> = Supplier { delegate.get(key, valueLoader) }
        if (retry != null) {
            supplier = Retry.decorateSupplier(retry, supplier)
        }

        val decoratedSupplier = CircuitBreaker.decorateSupplier<T?>(circuitBreaker, supplier)

        return if (silentOnCacheGetFailure) {
            runCatching {
                decoratedSupplier.get()
            }.onFailure {
                log.error("[Cache] Error occurred while getting cached object. fail-over to return null value.", it)
            }.getOrNull()
        } else {
            decoratedSupplier.get()
        }
    }

    override fun put(
        key: Any,
        value: Any?,
    ) {
        val retry: Retry? = retryPolicyProvider?.get()

        var callable = Callable { delegate.put(key, value) }
        if (retry != null) {
            callable = Retry.decorateCallable(retry, callable)
        }

        val decoratedRunnable = CircuitBreaker.decorateCallable(circuitBreaker, callable)

        if (silentOnCachePutFailure) {
            runCatching {
                decoratedRunnable.call()
            }.getOrNull()
        } else {
            decoratedRunnable.call()
        }
    }

    override fun putIfAbsent(
        key: Any,
        value: Any?,
    ): Cache.ValueWrapper? {
        val retry: Retry? = retryPolicyProvider?.get()

        var callable = Callable { delegate.putIfAbsent(key, value) }
        if (retry != null) {
            callable = Retry.decorateCallable(retry, callable)
        }

        val decoratedRunnable = CircuitBreaker.decorateCallable(circuitBreaker, callable)

        return if (silentOnCachePutFailure) {
            runCatching {
                decoratedRunnable.call()
            }.getOrElse {
                log.error("[Cache] Error occurred while putting cached object. fail-over to return null value.", it)
                Cache.ValueWrapper { null }
            }
        } else {
            decoratedRunnable.call()
        }
    }

    override fun evict(key: Any) {
        val retry: Retry? = retryPolicyProvider?.get()

        var runnable = Runnable { delegate.evict(key) }
        if (retry != null) {
            runnable = Retry.decorateRunnable(retry, runnable)
        }

        val decoratedSupplier = CircuitBreaker.decorateRunnable(circuitBreaker, runnable)

        if (silentOnCacheEvictFailure) {
            runCatching {
                decoratedSupplier.run()
            }.getOrNull()
        } else {
            decoratedSupplier.run()
        }
    }

    override fun clear() {
        val retry: Retry? = retryPolicyProvider?.get()

        var runnable = Runnable { delegate.clear() }
        if (retry != null) {
            runnable = Retry.decorateRunnable(retry, runnable)
        }

        val decoratedSupplier = CircuitBreaker.decorateRunnable(circuitBreaker, runnable)

        if (silentOnCacheClearFailure) {
            runCatching {
                decoratedSupplier.run()
            }.getOrNull()
        } else {
            decoratedSupplier.run()
        }
    }
}
