package com.cdg.skeleton.domain.telegram

import kotlin.reflect.KClass

@TelegramSpec(length = 4)
open class SafeClickTelegram(
    @TelegramField(index = 1, length = 12)
    open val header: String = "CCISDACOMPAY",
    @TelegramField(index = 2, length = 4)
    open val messageType: MessageType = MessageType.APPROVAL_REQUEST,
    @TelegramField(index = 3, length = 16)
    open val bitMap: BitMap,
) {
    enum class MessageType(
        val code: String,
        val availableTypes: List<KClass<out Telegram>>,
    ) {
        APPROVAL_REQUEST("0200", listOf(SafeClickApprovalRequestTelegram::class)),
        ;

        //        APPROVAL_RESPONSE("0210", listOf(SafeClickApprovalResponseTelegram::class)),

        constructor(code: String, availableType: KClass<out Telegram>) : this(code, listOf(availableType))
    }

    enum class BitMap(
        val code: String,
        val availableTypes: List<KClass<out Telegram>>,
    ) {
        APPROVAL_REQUEST("322004002880860A", listOf(SafeClickApprovalRequestTelegram::class)),
        ;

        constructor(code: String, availableType: KClass<out Telegram>) : this(code, listOf(availableType))
    }
}
