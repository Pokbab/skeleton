package com.cdg.skeleton.domain.telegram

import com.cdg.skeleton.common.extensions.ReflectionUtils
import com.cdg.skeleton.common.extensions.parseToYyyyMMdd
import com.cdg.skeleton.common.extensions.parseToYyyyMMddHHmmss
import com.cdg.skeleton.common.vo.privacy.CardNumberPrivacy
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties

object TelegramDecoder {
    fun <T : Telegram> decode(
        payload: TelegramString,
        telegramClass: KClass<T>,
    ): T {
        val telegramSpec =
            telegramClass.findAnnotation<TelegramSpec>()
                ?: throw IllegalArgumentException("TelegramSpec 설정이 존재하지 않습니다.")

        val fieldLengthOfTotalLength = telegramSpec.length
        val charset = telegramSpec.charset.charset
        val payloadByteArray = payload.toByteArray(charset)

        var offset = fieldLengthOfTotalLength
        val valuedParameters =
            telegramClass.memberProperties
                .sortedBy { it.findAnnotation<TelegramField>()?.index }
                .filter { it.findAnnotation<TelegramField>() != null }
                .mapNotNull { field ->
                    val telegramField = field.findAnnotations<TelegramField>().first()
                    var value = ""

                    when (telegramField.lengthType) {
                        LengthType.FIXED -> {
                            require(telegramField.length > 0) { "필드 길이는 0보다 커야 합니다." }
                            val fieldLength = telegramField.fieldLength
                            value = String(payloadByteArray, offset + fieldLength, telegramField.length - fieldLength, charset)
                            offset += telegramField.length
                        }
                        LengthType.VARIABLE -> {
                            require(telegramField.fieldLength > 0) { "길이 필드 길이는 0보다 커야 합니다." }
                            val fieldLength = String(payloadByteArray, offset, telegramField.fieldLength).toInt()
                            value = String(payloadByteArray, telegramField.fieldLength + offset, fieldLength, charset)
                            offset += telegramField.fieldLength + fieldLength
                        }
                    }

                    val fieldType = field.returnType.classifier as KClass<*>
                    val processed =
                        when {
                            fieldType == String::class -> value.trim()
                            fieldType == Int::class -> value.toInt()
                            fieldType == LocalDateTime::class -> {
                                if (telegramField.functionName.isNotEmpty()) {
                                    ReflectionUtils.callFunctionInCompanionObject(
                                        telegramClass,
                                        telegramField.functionName,
                                        value,
                                    ) as LocalDateTime?
                                        ?: value.parseToYyyyMMddHHmmss()
                                } else {
                                    value.parseToYyyyMMddHHmmss()
                                }
                            }
                            fieldType == LocalDate::class -> {
                                if (telegramField.functionName.isNotEmpty()) {
                                    ReflectionUtils.callFunctionInCompanionObject(
                                        telegramClass,
                                        telegramField.functionName,
                                        value,
                                    ) as LocalDate?
                                        ?: value.parseToYyyyMMdd()
                                } else {
                                    value.parseToYyyyMMdd()
                                }
                            }
                            fieldType.isSubclassOf(Enum::class) ->
                                fieldType.java.enumConstants.find { ReflectionUtils.getter(it, "code") == value }
                                    ?: throw IllegalArgumentException("스펙에 맞지 않는 값입니다. not found enum : $value, field: ${field.name}")
                            fieldType == CardNumberPrivacy::class -> CardNumberPrivacy(value)
                            else -> value
                        }
                    processed
                }
        return telegramClass.constructors.first().call(*valuedParameters.toTypedArray())
    }
}
