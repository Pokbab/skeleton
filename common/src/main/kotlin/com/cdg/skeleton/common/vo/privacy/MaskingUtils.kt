package com.cdg.skeleton.common.vo.privacy

private const val maskingChar = "*"

internal fun maskingMiddle(
    target: String,
    plainHeadCount: Int = 0,
    plainTailCount: Int = 0,
): String {
    if (target.isBlank()) return ""

    val length = target.length

    if (length <= plainHeadCount + plainTailCount) return target

    val plainHead = target.substring(0, plainHeadCount)
    val masking = maskingChar.repeat(length - plainHeadCount - plainTailCount)
    val plainTail = target.substring(plainHeadCount + masking.length, length)

    return "$plainHead$masking$plainTail"
}

internal fun maskingMiddleRange(
    target: String,
    start: Int = 0,
    end: Int = 0,
): String {
    if (target.isBlank()) return ""

    val stringBuilder = StringBuilder()
    target.forEachIndexed { index, c ->
        when {
            index < start -> stringBuilder.append(c)
            index in start..end -> stringBuilder.append("*")
            else -> stringBuilder.append(c)
        }
    }
    return stringBuilder.toString()
}

internal fun maskingEdge(
    target: String,
    maskingHeadCount: Int = 0,
    maskingTailCount: Int = 0,
): String {
    if (target.isBlank()) return ""

    val length = target.length
    if (length <= maskingHeadCount + maskingTailCount) return maskingChar.repeat(length)

    val maskingHead = maskingChar.repeat(maskingHeadCount)
    val plain = target.substring(maskingHeadCount, length - maskingTailCount)
    val maskingTail = maskingChar.repeat(maskingTailCount)

    return "$maskingHead$plain$maskingTail"
}
