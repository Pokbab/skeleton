package com.cdg.skeleton.api.config.cache

import java.time.Duration

class RedisLayer(
    val cacheName: String,
    val expiredAfterWrite: Duration,
)
