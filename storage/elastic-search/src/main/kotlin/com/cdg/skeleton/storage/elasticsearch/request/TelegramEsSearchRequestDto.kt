package com.cdg.skeleton.storage.elasticsearch.request

import co.elastic.clients.elasticsearch._types.query_dsl.Operator
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders
import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.DateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class TelegramEsSearchRequestDto(
    val message: String,
    val timestampRange: ClosedRange<ZonedDateTime>,
    val pageable: Pageable = Pageable.unpaged(),
) {
    fun build(): Query {
        val boolQuery = QueryBuilders.bool()!!
        boolQuery.must(
            QueryStringQuery.of { q -> q.defaultField("message").query(message).defaultOperator(Operator.And) }._toQuery(),
            QueryStringQuery.of { q -> q.defaultField("message").query("요청전문").defaultOperator(Operator.And) }._toQuery(),
            RangeQuery.of { r ->
                r.field("@timestamp")
                    .from(timestampRange.start.format(DateTimeFormatter.ofPattern(DateFormat.date_time.pattern)))
                    .to(timestampRange.endInclusive.format(DateTimeFormatter.ofPattern(DateFormat.date_time.pattern)))
            }._toQuery(),
        )
        return boolQuery.build()._toQuery()
    }
}
