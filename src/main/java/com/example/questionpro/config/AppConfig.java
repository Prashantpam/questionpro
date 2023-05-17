package com.example.questionpro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${urlStories}")
    private String urlStories;

    @Value("${urlId}")
    private String urlId;

    @Value("${batch_size:100}")
    private long batch;

    @Value("${top.stories:10}")
    private long topSize;

    @Value("${cache.time:15}")
    private long cacheTiming;

    public String getUrlStories() {
        return urlStories;
    }

    public String getUrlId() {
        return urlId;
    }

    public long getBatch() {
        return batch;
    }

    public long getTopSize() {
        return topSize;
    }

    public long getCacheTiming() {
        return cacheTiming;
    }
}
