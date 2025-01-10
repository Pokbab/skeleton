package com.cdg.skeleton.common.extensions

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

val yyyyMMddHHmmssFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
val yyyy_MM_dd_HH_mm_ssFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
val yyyyMMddFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
val yyyy_MM_dd_T_HH_mm_ss_x_Formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000'Z'")

fun String.parseTimestamp(): LocalDateTime {
    val decimalValue = BigDecimal(this)
    val seconds = decimalValue.toLong()
    val nanoAdjustment = ((decimalValue - BigDecimal(seconds)) * BigDecimal(1_000_000_000)).toLong()
    val instant = Instant.ofEpochSecond(seconds, nanoAdjustment)
    return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"))
}

fun String.parseToDefaultLocalDateTime(): LocalDateTime {
    val patterns = listOf(
        "yyyy-MM-dd HH:mm:ss" to LocalDateTime::class,
        "yyyy-MM-dd'T'HH:mm:ss" to LocalDateTime::class,
        "yyyyMMddHHmmss" to LocalDateTime::class,
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" to OffsetDateTime::class
    )
    for (pattern in patterns) {
        try {
            val formatter = DateTimeFormatterBuilder()
                .appendPattern(pattern.first)
                .optionalStart()
                .appendFraction(ChronoField.NANO_OF_SECOND, 0, 6, true)
                .optionalEnd()
                .toFormatter()
            when (pattern.second) {
                LocalDateTime::class -> return LocalDateTime.parse(this, formatter)
                OffsetDateTime::class -> return OffsetDateTime.parse(this, formatter).toLocalDateTime()
            }
        } catch (e: Exception) {
            continue
        }
    }
    throw IllegalArgumentException("Invalid date format: $this")
}