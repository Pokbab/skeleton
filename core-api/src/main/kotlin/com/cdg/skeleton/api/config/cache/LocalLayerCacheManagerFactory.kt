package com.cdg.skeleton.api.config.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import java.util.concurrent.TimeUnit

class LocalLayerCacheManagerFactory {
    companion object {
        fun create(caches: List<LocalLayer>): CacheManager {
            val cacheManager = CaffeineCacheManager()
            caches.forEach { c ->
                cacheManager.registerCustomCache(
                    c.cacheName,
                    Caffeine.newBuilder()
                        .expireAfterWrite(c.expiredAfterWrite.toMillis(), TimeUnit.MILLISECONDS)
                        .maximumSize(c.maximumSize)
                        .recordStats()
                        .build(),
                )
            }
            return cacheManager
        }
    }
}
