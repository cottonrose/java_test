package com.bittech.tangshianalyze.analyze.service;

import com.bittech.tangshianalyze.analyze.entity.PoetryInfo;

import java.util.List;
import java.util.Map;

/**
 * Author: Cottonrose
 * Created: 2018/12/11
 */
public interface AnalyzeService {
    
    List<PoetryInfo> queryPoetryInfoByTitle(String title);
    
    List<PoetryInfo> queryPoetryInfoByAuthor(String author);
    
    Map<String, Integer> creationRankingAnalyze();
    
    Map<String,Integer> cloudWords();
    
}
