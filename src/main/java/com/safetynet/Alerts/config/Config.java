package com.safetynet.Alerts.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration class.
 *
 * @author Laura Habdul
 */
@Configuration
public class Config {

    /**
     * Enables httptrace endpoint (disabled by default from SpringBoot 2.2.0).
     *
     * @return HttpTraceRepository instance
     */
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}