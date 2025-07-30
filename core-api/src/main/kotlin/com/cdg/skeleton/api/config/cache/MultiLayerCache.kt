package com.cdg.skeleton.api.config.cache

import org.springframework.cache.Cache
import org.springframework.cache.support.SimpleValueWrapper
import java.util.concurrent.Callable

class MultiLayerCache(
    private val caches: List<Cache>,
) : Cache {
    private val first: Int = 0
    private val size: Int = caches.size

    private val cacheName = "MultiLayerCache[${caches.joinToString(",") { it.name }}]"
    private val reverseOrderCache: List<Cache> = caches.reversed()

    override fun getName(): String {
        return cacheName
    }

    override fun getNativeCache(): Any {
        return this
    }

    override fun get(key: Any): Cache.ValueWrapper? {
        for (cacheTierIndex in first until size) {
            val valueWrapper: Cache.ValueWrapper? = caches[cacheTierIndex].get(key)
            val value = valueWrapper?.get()
            if (value != null) {
                if (cachedDataFoundOnFirstLevelCache(cacheTierIndex)) {
                    return valueWrapper
                }

                cachingCacheTargetDataToAllLowerTierCache(cacheTierIndex, key, value)
                return Cache.ValueWrapper { value }
            }
        }
        return null
    }

    override fun <T : Any?> get(
        key: Any,
        type: Class<T>?,
    ): T? {
        for (cacheTierIndex in first until size) {
            val value: T? = caches[cacheTierIndex].get(key, type)
            if (value != null) {
                if (cachedDataFoundOnFirstLevelCache(cacheTierIndex)) {
                    return value
                }

                cachingCacheTargetDataToAllLowerTierCache(cacheTierIndex, key, value)
                return value
            }
        }
        return null
    }

    override fun <T : Any?> get(
        key: Any,
        valueLoader: Callable<T>,
    ): T? {
        val value: T =
            try {
                valueLoader.call()
            } catch (e: Exception) {
                throw Cache.ValueRetrievalException(key, valueLoader, e)
            }
        put(key, value)
        return value
    }

    override fun put(
        key: Any,
        value: Any?,
    ) {
        for (cache in reverseOrderCache) {
            cache.put(key, value)
        }
    }

    override fun putIfAbsent(
        key: Any,
        value: Any?,
    ): Cache.ValueWrapper {
        for (cache in reverseOrderCache) {
            cache.putIfAbsent(key, value)
        }
        return SimpleValueWrapper(value)
    }

    override fun evict(key: Any) {
        for (cache in reverseOrderCache) {
            cache.evict(key)
        }
    }

    override fun clear() {
        for (cache in reverseOrderCache) {
            cache.clear()
        }
    }

    private fun cachedDataFoundOnFirstLevelCache(cacheTierIndex: Int): Boolean {
        return cacheTierIndex == first
    }

    private fun cachingCacheTargetDataToAllLowerTierCache(
        cacheTierIndex: Int,
        key: Any,
        value: Any,
    ) {
        for (i in cacheTierIndex - 1 downTo first) {
            val nTierCache: Cache = caches[i]
            nTierCache.put(key, value)
        }
    }
}
