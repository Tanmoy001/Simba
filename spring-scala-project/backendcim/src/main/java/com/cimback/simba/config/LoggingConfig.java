// src/main/java/com/example/config/LoggingConfig.java
package com.cimback.simba.config;

import com.cimback.simba.logging.ApiLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class LoggingConfig {

    private final DataSource dataSource;

    public LoggingConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ApiLogger apiLogger() {
        return new ApiLogger(dataSource);
    }
}