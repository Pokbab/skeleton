package com.cdg.skeleton.api.config.cache

data class LocalAndRedisCachesList(
    val localCaches: List<LocalLayer>,
    val redisCaches: List<RedisLayer>,
)
