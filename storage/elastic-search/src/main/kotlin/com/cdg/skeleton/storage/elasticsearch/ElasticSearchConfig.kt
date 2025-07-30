package com.cdg.skeleton.storage.elasticsearch

import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import java.time.Duration
import java.time.temporal.ChronoUnit
import javax.net.ssl.SSLContext

@Configuration
internal class ElasticSearchConfig(
    private val properties: ElasticSearchProperties,
) {
    internal fun clientConfituation(): ClientConfiguration {
        val hostAndPorts = properties.hostAndPorts.replace("https://", "")
        val hosts = hostAndPorts.split(",").toTypedArray()
        return setHostsAndSsl(hosts, properties.usingSsl)
            .withConnectTimeout(Duration.ofSeconds(15))
            .withSocketTimeout(Duration.of(15, ChronoUnit.SECONDS))
            .build()
    }

    private fun setHostsAndSsl(
        hosts: Array<String>,
        usingSsl: Boolean,
    ): ClientConfiguration.MaybeSecureClientConfigurationBuilder {
        return ClientConfiguration.builder()
            .connectedTo(*hosts)
            .apply {
                if (usingSsl) {
                    usingSsl(SSLContext.getDefault())
                }
            }
    }
}
