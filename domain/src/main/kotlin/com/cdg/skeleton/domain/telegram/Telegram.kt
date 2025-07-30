package com.cdg.skeleton.domain.telegram

import kotlin.reflect.KClass

interface Telegram {
    fun availableType(): KClass<out Telegram>
}

interface RequestTelegram : Telegram

interface ResponseTelegram : Telegram

typealias TelegramString = String
