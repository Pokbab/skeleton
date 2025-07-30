package com.cdg.skeleton.storage.elasticsearch

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "elasticsearch.server")
internal data class ElasticSearchProperties(
    val hostAndPorts: String,
    val usingSsl: Boolean,
)
