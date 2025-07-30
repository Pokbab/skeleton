package com.cdg.skeleton.storage.elasticsearch

import com.cdg.skeleton.domain.telegram.LogClient
import com.cdg.skeleton.domain.telegram.TelegramString
import com.cdg.skeleton.storage.elasticsearch.request.TelegramEsSearchRequestDto
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId

@Component
internal class LogClientAdaptor(
    private val elasticSearcher: ElasticSearcher,
) : LogClient {
    override fun findTelegram(
        transactionId: String,
        dataTime: LocalDateTime,
    ): TelegramString? {
        val zoneId = ZoneId.of("Asia/Seoul")
        val request =
            TelegramEsSearchRequestDto(
                message = transactionId,
                timestampRange = dataTime.withHour(0).atZone(zoneId)..dataTime.atZone(zoneId).plusDays(1),
            )
        val response = elasticSearcher.searchTelegram(request)
        return response.content.lastOrNull()?.message?.let { it.substringAfter("요청전문 : ") }
            ?: return null
    }
}
