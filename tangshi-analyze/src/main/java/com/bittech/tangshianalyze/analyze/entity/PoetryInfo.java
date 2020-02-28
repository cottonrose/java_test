package com.bittech.tangshianalyze.analyze.entity;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author: Cottonrose
 * Created: 2018/1/15 0015
 */
@Data
public class PoetryInfo {
    
    /**
     * 编号
     */
    private String metaId;
    
    /**
     * 来源地址
     */
    private String metaUrl;
    
    
    /**
     * 创建时间
     */
    private LocalDateTime metaCreate;
    
    /**
     * 作者名称
     */
    private String authorName;
    
    /**
     * 作者所在朝代
     */
    private String authorDynasty;
    
    /**
     * 标题
     */
    private String contentTitle;
    
    /**
     * 正文
     */
    private String contentBody;
    
}
