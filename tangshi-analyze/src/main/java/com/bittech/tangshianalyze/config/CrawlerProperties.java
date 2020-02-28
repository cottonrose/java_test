package com.bittech.tangshianalyze.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: Cottonrose
 * Created: 2018/12/13
 */
@ConfigurationProperties(prefix = "crawler")
@Component
@Data
public class CrawlerProperties {
    
    private String base;
    
    private String path;
    
    private Integer thread;
}

