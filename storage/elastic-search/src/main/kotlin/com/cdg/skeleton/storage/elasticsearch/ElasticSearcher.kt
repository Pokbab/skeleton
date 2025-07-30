package com.cdg.skeleton.storage.elasticsearch

import com.cdg.skeleton.storage.elasticsearch.request.TelegramEsSearchRequestDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Sort
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHitSupport
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.search
import org.springframework.stereotype.Component

@Component
class ElasticSearcher(
    @Qualifier("logElasticSearchClient") private val elasticSearchOperations: ElasticsearchOperations,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun searchTelegram(request: TelegramEsSearchRequestDto): Page<LogV1Document> {
        val query = request.build()
        log.info("[ElasticSearcher.searchTelegram()] query: $query")
        val response =
            SearchHitSupport.searchPageFor(
                elasticSearchOperations.search(
                    NativeQueryBuilder()
                        .withQuery(query)
                        .withTrackTotalHits(true)
                        .withPageable(request.pageable)
                        .withSort(Sort.by(Sort.Direction.DESC, "@timestamp"))
                        .build(),
                    LogV1Document::class.java,
                    IndexCoordinates.of(*listOf("aws-log-es:log-v1-*", "dc1-log-es:log-v1-*").toTypedArray()),
                ),
                request.pageable,
            )
        return PageImpl(
            response.content.map { it.content },
            request.pageable,
            response.totalElements,
        )
    }
}
