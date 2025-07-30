package com.cdg.skeleton.api.config.cache

import java.time.Duration

class MultiLayerCacheList {
    companion object {
        fun builder(): MultiLayerCacheBuilder {
            return MultiLayerCacheBuilder()
        }
    }

    class MultiLayerCacheBuilder(
        private val cacheConfigs: MutableList<Pair<LocalLayer, RedisLayer>> = mutableListOf(),
    ) {
        fun addCacheLayer(
            cacheName: String,
            localMaxCacheItem: Long = 5000,
            localCacheDuration: Duration,
            redisCacheDuration: Duration,
        ): MultiLayerCacheBuilder {
            cacheConfigs.add(
                Pair(
                    LocalLayer(cacheName, localCacheDuration, localMaxCacheItem),
                    RedisLayer(cacheName, redisCacheDuration),
                ),
            )
            return this
        }

        fun build(): LocalAndRedisCachesList {
            return LocalAndRedisCachesList(
                localCaches = cacheConfigs.map { it.first }.toList(),
                redisCaches = cacheConfigs.map { it.second }.toList(),
            )
        }
    }
}
