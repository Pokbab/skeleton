package com.cdg.skeleton.domain.telegram

import java.nio.charset.Charset
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class TelegramSpec(
    val length: Int,
    val charset: TelegramCharSet = TelegramCharSet.EUC_KR,
    val dependsOnSpecClass: KClass<*> = Nothing::class,
)

@Target(AnnotationTarget.PROPERTY)
annotation class TelegramField(
    val index: Int,
    val length: Int,
    val fieldLength: Int = 0,
    val lengthType: LengthType = LengthType.FIXED,
    val paddingType: PaddingType = PaddingType.RIGHT,
    val padChar: Char = ' ',
    val functionName: String = "",
    val optional: Boolean = false,
    val ignoreCompare: Boolean = false,
)

enum class LengthType {
    FIXED,
    VARIABLE,
}

enum class PaddingType {
    LEFT,
    RIGHT,
}

enum class TelegramCharSet(
    val charset: Charset,
) {
    EUC_KR(Charset.forName("EUC-KR")),
    UTF_8(Charset.forName("UTF-8")),
}

annotation class TelegramDependsOnSpec
