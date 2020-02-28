package com.bittech.tangshianalyze.crawler.pipeline;

import com.bittech.tangshianalyze.crawler.common.DataSet;

/**
 * Author: Cottonrose
 * Created: 2018/12/11
 */
public interface Pipeline {
    
    void process(DataSet dataSet);
}
