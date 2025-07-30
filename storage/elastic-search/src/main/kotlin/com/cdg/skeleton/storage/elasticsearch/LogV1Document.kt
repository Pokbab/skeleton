package com.cdg.skeleton.storage.elasticsearch

import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.OffsetDateTime

data class LogV1Document(
    @Field(
        name = "@timestamp",
        type = FieldType.Date,
        pattern = ["yyyy-MM-dd'T'HH:mm:ssXXX", "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", "yyyy-MM-dd'T'HH:mm:ss.SSSSXXX"],
    )
    val timestamp: OffsetDateTime?,
    @Field(name = "message", type = FieldType.Keyword)
    val message: String,
)
