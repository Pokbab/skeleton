package com.cdg.skeleton.api.config.cache

import java.time.Duration

data class LocalLayer(
    val cacheName: String,
    val expiredAfterWrite: Duration,
    val maximumSize: Long,
)
