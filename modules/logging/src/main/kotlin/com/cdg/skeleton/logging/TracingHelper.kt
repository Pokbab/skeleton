package com.cdg.skeleton.logging

import io.micrometer.context.ContextExecutorService
import io.micrometer.context.ContextSnapshot
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.core.task.TaskDecorator
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

object TracingHelper {
    fun buildContextExecutor(executor: ExecutorService): Executor {
        @Suppress("DEPRECATION")
        return ContextExecutorService.wrap(executor, ContextSnapshot::captureAll)
    }

    fun mdcTaskDecorator(): TaskDecorator {
        return MdcTaskDecorator()
    }
}

class MdcTaskDecorator : TaskDecorator {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun decorate(runnable: Runnable): Runnable {
        val mdcContext = MDC.getCopyOfContextMap()
        return Runnable {
            try {
                if (mdcContext != null) {
                    MDC.setContextMap(mdcContext)
                }
                runnable.run()
            } catch (e: Exception) {
                log.warn("traceId[{}] excaption occurred while execution @Async method", tryGetTraceIdSafely(), e)
                throw e
            } finally {
                MDC.clear()
            }
        }
    }

    private fun tryGetTraceIdSafely(): String {
        return try {
            val mdcContext = MDC.getCopyOfContextMap()
            mdcContext?.getOrDefault("traceId", "UNKNOWN") ?: "UNKNOWN"
        } catch (e: Exception) {
            "UNKNOWN"
        }
    }
}