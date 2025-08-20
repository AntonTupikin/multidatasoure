package com.example.multidatasoure.utils;

import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.unit.DataSize;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Map;

public class WebClientBuilder {
    private WebClient.Builder builder;
    private Duration timeout = Duration.ofSeconds(10);
    private String baseUrl = "";
    private Map<String, String> headers = Map.of();

    public WebClientBuilder(WebClient.Builder builder) {
        this.builder = builder;
    }

    public WebClient build() {
        HttpClient client = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) timeout.toMillis())
                .responseTimeout(timeout)
                .followRedirect(true);
        builder = builder
                .codecs(clientCodecConfigurer -> clientCodecConfigurer
                        .defaultCodecs()
                        .maxInMemorySize((int) DataSize.ofMegabytes(10).toBytes()))
                .clientConnector(new ReactorClientHttpConnector(client));
        for (Map.Entry<String, String> header : headers.entrySet()) {
            builder = builder.defaultHeader(header.getKey(), header.getValue());
        }
        builder = builder.baseUrl(baseUrl);
        return builder.build();
    }

    public WebClientBuilder timeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public WebClientBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public WebClientBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
}
