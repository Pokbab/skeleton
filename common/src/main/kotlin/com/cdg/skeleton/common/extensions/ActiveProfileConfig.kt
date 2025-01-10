package com.cdg.skeleton.common.extensions

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("spring.profiles")
class ActiveProfileConfig {

    lateinit var active: String

    init {
        if (System.getProperty("spring.profiles.active") == null) {
            System.setProperty("spring.profiles.active", active)
        }
    }
}

fun serverProfile() = System.getProperty("spring.profiles.active", "")!!

fun isLive(): Boolean = serverProfile() == "live"

fun isDev(): Boolean = serverProfile() == "dev"

fun isLocal(): Boolean = serverProfile() == "local"