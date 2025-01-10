package com.cdg.skeleton.api.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController {
    private val log = KotlinLogging.logger {}

    @GetMapping("/test")
    fun test(): String {
        log.info { "TestController.test()" }
        return "Hello World"
    }
}