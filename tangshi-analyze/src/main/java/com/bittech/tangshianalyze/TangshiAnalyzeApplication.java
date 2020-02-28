package com.bittech.tangshianalyze;

import com.bittech.tangshianalyze.config.CrawlerProperties;
import com.bittech.tangshianalyze.crawler.Crawler;
import com.bittech.tangshianalyze.crawler.pipeline.Pipeline;
import com.bittech.tangshianalyze.crawler.prase.Parse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TangshiAnalyzeApplication {
    
    @Autowired
    private CrawlerProperties crawlerProperties;
    
    @Bean
    @Autowired
    public Crawler crawler(List<Pipeline> pipelines, List<Parse> parses) {
        Crawler crawler;
        crawler = new Crawler(crawlerProperties.getBase(), crawlerProperties.getPath());
        pipelines.forEach(crawler::addPipeline);
        parses.forEach(crawler::addParse);
        return crawler;
    }
    
    private static void startCrawler(ApplicationContext context) {
        Crawler crawler = context.getBean(Crawler.class);
        crawler.start();
    }
    
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TangshiAnalyzeApplication.class, args);
        //取消注释可以启动爬虫
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("crawler-run")) {
                startCrawler(context);
            }
        }
    }
}