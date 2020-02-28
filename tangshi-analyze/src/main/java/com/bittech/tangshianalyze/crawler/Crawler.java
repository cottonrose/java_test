package com.bittech.tangshianalyze.crawler;

import com.bittech.tangshianalyze.crawler.common.Page;
import com.bittech.tangshianalyze.crawler.pipeline.Pipeline;
import com.bittech.tangshianalyze.crawler.prase.Parse;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Cottonrose
 * Created: 2018/12/11
 */
public class Crawler {
    
    private final Logger logger = LoggerFactory.getLogger(Crawler.class);
    
    private LinkedBlockingQueue<Page> pageQueue = new LinkedBlockingQueue<>();
    
    private LinkedBlockingQueue<Page> dataQueue = new LinkedBlockingQueue<>();
    
    private List<Pipeline> pipelineList = new ArrayList<>();
    
    private List<Parse> parseList = new ArrayList<>();
    
    private ExecutorService executorService;
    
    private WebClient webClient;
    
    public Crawler(String base, String path) {
        this.initWork();
        this.pageQueue.add(new Page(path, base));
    }
    
    //初始化工作
    private void initWork() {
        this.executorService = Executors.newFixedThreadPool(4, new ThreadFactory() {
            final AtomicInteger id = new AtomicInteger(0);
            
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Crawler-Thread-" + id.getAndIncrement());
            }
        });
        this.webClient = new WebClient(BrowserVersion.CHROME);
        this.webClient.getOptions().setJavaScriptEnabled(false);
        
    }
    
    public Crawler addPipeline(Pipeline pipeline) {
        this.pipelineList.add(pipeline);
        return this;
    }
    
    public Crawler addParse(Parse parse) {
        this.parseList.add(parse);
        return this;
    }
    
    private void parse() {
        while (true) {
            try {
                Thread.sleep(1000);
                final Page page = pageQueue.poll();
                if (page == null) {
                    continue;
                }
                executorService.submit(() -> {
                    try {
                        HtmlPage htmlPage = webClient.getPage(page.getUrl());
                        page.setHtmlPage(htmlPage);
                        Crawler.this.parseList.forEach(parse -> {
                                    parse.parse(page);
                                    try {
                                        if (page.isDetail()) {
                                            dataQueue.put(page);
                                        } else {
                                            Iterator<Page> iterator = page.getSubPage().iterator();
                                            while (iterator.hasNext()) {
                                                try {
                                                    pageQueue.put(iterator.next());
                                                    iterator.remove();
                                                } catch (InterruptedException e) {
                                                    logger.error("Queue put interrupted occur {} .", e.getMessage());
                                                }
                                            }
                                        }
                                    } catch (InterruptedException e) {
                                        logger.error("Queue put interrupted occur {} .", e.getMessage());
                                    }
                                }
                        );
                    } catch (IOException e) {
                        logger.error("Page fetch occur {} .", e.getMessage());
                    }
                });
            } catch (Exception e) {
                logger.error("Queue poll occur {} .", e.getMessage());
            }
        }
    }
    
    private void pipeline() {
        while (true) {
            try {
                Thread.sleep(1000);
                final Page page = dataQueue.poll();
                if (page == null) {
                    continue;
                }
                executorService.submit(() -> {
                    Crawler.this.pipelineList.forEach(pipeline -> {
                        pipeline.process(page.getDataSet());
                    });
                });
            } catch (Exception e) {
                logger.error("Pipeline data occur {} .", e.getMessage());
            }
        }
    }
    
    public void start() {
        executorService.execute(this::parse);
        executorService.execute(this::pipeline);
    }
    
    public void stop() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}