package com.bittech.tangshianalyze.analyze.dao;

import com.bittech.tangshianalyze.analyze.entity.PoetryInfo;

import java.util.List;

/**
 * Author: Cottonrose
 * Created: 2018/12/11
 */
public interface AnalyzeDao {
    
    List<PoetryInfo> loadAll();
}
