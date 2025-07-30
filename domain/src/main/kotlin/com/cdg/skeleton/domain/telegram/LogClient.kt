package com.cdg.skeleton.domain.telegram

import java.time.LocalDateTime

interface LogClient {
    fun findTelegram(
        transactionId: String,
        dataTime: LocalDateTime,
    ): TelegramString?
}
