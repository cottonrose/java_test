package com.bittech.tangshianalyze.crawler.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Cottonrose
 * Created: 2018/12/11
 */
@Getter
@Setter
public class Page {
    
    private final String path;
    
    private final String base;
    
    private HtmlPage htmlPage;
    
    private boolean detail;
    
    private Set<Page> subPage = new HashSet<>();
    
    private DataSet dataSet = new DataSet();
    
    public Page(String path, String base) {
        this.path = !path.startsWith("/") ? "/" + path : path;
        this.base = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
    }
    
    public void put(String key, Object value) {
        this.dataSet.putData(key, value);
    }
    
    public void put(Page subPage) {
        this.subPage.add(subPage);
    }
    
    public String getUrl() {
        return this.getBase() + this.getPath();
    }
}
