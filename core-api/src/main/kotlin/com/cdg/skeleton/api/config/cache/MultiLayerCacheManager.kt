package com.cdg.skeleton.api.config.cache

import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import java.util.concurrent.ConcurrentHashMap

class MultiLayerCacheManager(
    private val cacheManagers: List<CacheManager>,
    private val cacheMap: MutableMap<String, Cache> = ConcurrentHashMap(),
) : CacheManager {
    override fun getCache(name: String): Cache? {
        return cacheMap.computeIfAbsent(name) {
            MultiLayerCache(getCaches(it))
        }
    }

    private fun getCaches(name: String): List<Cache> {
        return cacheManagers.map { manager ->
            val message = "MultiLayerCacheManager requires all cache managers to support the same set of caches"
            return@map manager.getCache(name) ?: throw RuntimeException(message)
        }.toList()
    }

    override fun getCacheNames(): Collection<String> {
        return cacheManagers.flatMap { manager ->
            manager.cacheNames
        }.toList().toSet()
    }
}
