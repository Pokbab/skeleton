package com.cdg.skeleton.domain.telegram

import com.cdg.skeleton.common.extensions.parseToYyyyMMddHHmmss
import java.time.LocalDateTime
import java.time.Year

@TelegramSpec(length = 4, dependsOnSpecClass = SafeClickTelegram.BitMap::class)
abstract class SafeClickApprovalRequestTelegram internal constructor(
    @TelegramField(index = 1, length = 12)
    override val header: String,
    @TelegramField(index = 2, length = 4)
    override val messageType: MessageType = MessageType.APPROVAL_REQUEST,
    @TelegramField(index = 3, length = 16)
    override val bitMap: BitMap,
    @TelegramField(index = 7, length = 12)
    open val approvalRequestAmount: String,
    @TelegramField(index = 8, length = 16)
    open val cardNumber: String,
    @TelegramField(index = 6, length = 10, functionName = "decodeTransactionDate", ignoreCompare = true)
    open val transactionDate: String,
    @TelegramField(index = 10, length = 6)
    open val cardCvc: String,
    @TelegramField(index = 11, length = 20)
    open val cardHolderName: String,
    @TelegramField(index = 12, length = 20)
    open val cardHolderEmail: String,
    @TelegramField(index = 13, length = 20)
    open val cardHolderPhone: String,
    @TelegramField(index = 14, length = 20)
    open val cardHolderBirth: String,
    @TelegramField(index = 15, length = 20)
    open val cardHolderAddress: String,
    @TelegramField(index = 16, length = 20)
    open val cardHolderPostcode: String,
    @TelegramField(index = 17, length = 20)
    open val cardHolderCity: String,
    @TelegramField(index = 18, length = 20)
    open val cardHolderCountry: String,
    @TelegramField(index = 19, length = 20)
    open val cardHolderIp: String,
    @TelegramField(index = 20, length = 20)
    open val cardHolderUserAgent: String,
    @TelegramField(index = 21, length = 20)
    open val cardHolderReferer: String,
    @TelegramField(index = 22, length = 20)
    open val cardHolderMerchantId: String,
) : SafeClickTelegram(header, messageType, bitMap), RequestTelegram {
    companion object {
        fun decodeTransactionDate(value: String): LocalDateTime {
            return "${Year.now()}$value".parseToYyyyMMddHHmmss()
        }
    }
}
