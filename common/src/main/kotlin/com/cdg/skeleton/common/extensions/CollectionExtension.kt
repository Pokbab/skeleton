package com.cdg.skeleton.common.extensions

fun <T> List<T>.allOrFalse(predicate: (T) -> Boolean): Boolean {
    if (isEmpty()) {
        return false
    }
    return this.all(predicate)
}

fun MutableList<String>.addIfNeed(predicate: Boolean, message: String): MutableList<String> {
    if (predicate) {
        add(message)
    }
    return this
}

fun MutableList<String>.addIfNotBlank(value: String?, message: String): MutableList<String> {
    if (!value.isNullOrBlank()) {
        add(message)
    }
    return this
}

fun MutableList<String>.addIfNotBlank(value: String?, message: (String) -> String): MutableList<String> {
    if (!value.isNullOrBlank()) {
        add(message.invoke(value))
    }
    return this
}