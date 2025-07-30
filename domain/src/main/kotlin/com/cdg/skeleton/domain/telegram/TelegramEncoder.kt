package com.cdg.skeleton.domain.telegram

import com.cdg.skeleton.common.extensions.ReflectionUtils
import com.cdg.skeleton.common.vo.privacy.Privacy
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

object TelegramEncoder {
    fun <T : Telegram> encode(obj: T): String {
        val telegramClass = obj.javaClass.kotlin

        require(telegramClass.isData) { "Telegram 클래스는 data class여야 합니다." }

        val telegramSpec =
            telegramClass.findAnnotation<TelegramSpec>()
                ?: throw IllegalArgumentException("TelegramSpec 설정이 존재하지 않습니다.")

        val fieldLengthOfTotalLength = telegramSpec.length
        val dependsOnSpecType =
            telegramClass.memberProperties
                .firstOrNull { it.returnType.classifier == telegramSpec.dependsOnSpecClass }
                ?.call(obj)

        val stringBuilder = StringBuilder()
        telegramClass.memberProperties
            .sortedBy { it.findAnnotation<TelegramField>()?.index }
            .filterNot { field ->
                val dependsOnSpec = field.annotations.find { it.annotationClass.hasAnnotation<TelegramDependsOnSpec>() }
                dependsOnSpec != null && dependsOnSpec.annotationClass.memberProperties.first().call(dependsOnSpec) != dependsOnSpecType
            }
            .forEach { field ->
                val telegramField =
                    field.findAnnotations<TelegramField>().firstOrNull()
                        ?: throw IllegalArgumentException("${field.name} 설정이 존재하지 않습니다.")

                val processed =
                    when (val value = field.call(obj)) {
                        is LocalDateTime, is LocalDate -> {
                            if (telegramField.functionName.isNotEmpty()) {
                                ReflectionUtils.callFunctionInCompanionObject(telegramClass, telegramField.functionName, value) as String?
                                    ?: ""
                            } else {
                                ""
                            }
                        }
                        is Privacy -> value.plain
                        is Enum<*> -> ReflectionUtils.getter(value, "code") as String
                        else -> value.toString()
                    }

                var paddedValue = getPaddedValue(processed, telegramField)

                paddedValue =
                    when (telegramField.lengthType) {
                        LengthType.FIXED -> {
                            require(telegramField.length > 0) { "필드 길이는 0보다 커야 합니다." }
                            require(telegramField.length >= paddedValue.length) {
                                "변환된 전문의 길이가 지정된 length 보다 깁니다. field length: ${telegramField.length}, value length: ${paddedValue.length}"
                            }
                            paddedValue
                        }
                        LengthType.VARIABLE -> {
                            require(telegramField.fieldLength > 0) { "길이 필드 길이는 0보다 커야 합니다." }
                            val value = getPaddedValue(paddedValue, telegramField)
                            val fieldLength = value.length.toString().padStart(telegramField.fieldLength, '0')
                            fieldLength + value
                        }
                    }
                stringBuilder.append(paddedValue)
            }

        val totalLength = stringBuilder.length.toString().padStart(fieldLengthOfTotalLength, '0')

        return totalLength + stringBuilder.toString()
    }

    private fun getPaddedValue(
        value: String,
        telegramField: TelegramField,
    ): String {
        return when (telegramField.paddingType) {
            PaddingType.LEFT -> value.padStart(telegramField.length, telegramField.padChar)
            PaddingType.RIGHT -> value.padEnd(telegramField.length, telegramField.padChar)
        }
    }
}
