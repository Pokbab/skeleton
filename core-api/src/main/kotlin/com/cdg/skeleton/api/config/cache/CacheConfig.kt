package com.cdg.skeleton.api.config.cache

// @EnableCaching
// @Configuration
// class CacheConfig(
//    private val redisConnectionFactory: LettuceConnectionFactory,
// ) {
//    val multiLayerCacheKeys = MultiLayerCacheList.builder()
//        .addCacheLayer(
//            cacheName = CacheKey.MultiLayer.CACHE_NAME_SAMPLE,
//            localMaxCacheItem = 2500,
//            localCacheDuration = Duration.ofSeconds(5),
//            redisCacheDuration = Duration.ofSeconds(60),
//        )
//        .build()
//
//    @Bean(CacheKey.MultiLayer.CACHE_MANAGER_NAME)
//    fun multiLayerCacheManager(): CacheManager {
//        val circuitBreakerConfig = CircuitBreakerConfig
//            .custom()
//            .failureRateThreshold(50f)
//            .waitDurationInOpenState(Duration.ofSeconds(5))
//            .permittedNumberOfCallsInHalfOpenState(5)
//            .slidingWindowSize(500)
//            .minimumNumberOfCalls(250)
//            .recordExceptions(Exception::class.java, RuntimeException::class.java)
//            .build()
//        val circuitBreaker = CircuitBreaker.of("redisCacheManagerCircuitBreaker", circuitBreakerConfig)
//        val ec2CircuitBreakerRedisCacheManager = CircuitBreakerCacheManager(
//            delegate = ec2RedisCacheManagerForMultiLayerCache(multiLayerCacheKeys.redisCaches),
//            circuitBreaker = circuitBreaker,
//            silentOnCacheGetFailure = true,
//            silentOnCachePutFailure = false,
//            silentOnCacheEvictFailure = false,
//            silentOnCacheClearFailure = false,
//            retryPolicyProvider = {
//                Retry.of(
//                    "redisCacheManagerRetryPolicy",
//                    RetryConfig.custom<RetryConfig>()
//                        .maxAttempts(3)
//                        .intervalFunction(IntervalFunction.ofExponentialBackoff(Duration.ofMillis(100)))
//                        .build(),
//                )
//            },
//        )
//        return MultiLayerCacheManager(
//            listOf(
//                LocalLayerCacheManagerFactory.create(multiLayerCacheKeys.localCaches),
//                ec2CircuitBreakerRedisCacheManager,
//            ),
//        )
//    }
//
//    private fun ec2RedisCacheManagerForMultiLayerCache(caches: List<RedisLayer>): CacheManager {
//        val redisCacheConfigurationMap = caches.map { c ->
//            c.cacheName to RedisCacheConfiguration.defaultCacheConfig()
//                .prefixCacheNameWith(c.cacheName + "-")
//                .entryTtl(c.expiredAfterWrite)
//        }.toMap()
//
//        val redisCacheMapper = RedisCacheManager
//            .RedisCacheManagerBuilder
//            .fromConnectionFactory(redisConnectionFactory)
//            .withInitialCacheConfigurations(redisCacheConfigurationMap)
//            .build()
//
//        redisCacheMapper.afterPropertiesSet()
//        return redisCacheMapper
//    }
// }
