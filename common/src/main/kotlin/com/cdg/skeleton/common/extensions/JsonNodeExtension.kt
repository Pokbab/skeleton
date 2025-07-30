package com.cdg.skeleton.common.extensions

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun JsonNode.extractField(fieldPath: String): JsonNode {
    val fieldNames = fieldPath.split(".")
    var result: JsonNode = this
    fieldNames.forEach {
        result = result.get(it) ?: jacksonObjectMapper().nullNode()
    }
    return result
}

fun JsonNode.updateField(
    fieldPath: String,
    newValue: String,
): JsonNode {
    val fieldNames = fieldPath.split(".")
    var currentNode: JsonNode = this

    for (i in 0 until fieldNames.size - 1) {
        currentNode = currentNode.get(fieldNames[i]) ?: return this
    }
    if (currentNode is ObjectNode) {
        currentNode.put(fieldNames.last(), newValue)
    }
    return this
}
