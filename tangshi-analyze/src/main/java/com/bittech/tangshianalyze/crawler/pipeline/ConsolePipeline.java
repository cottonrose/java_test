package com.bittech.tangshianalyze.crawler.pipeline;

import com.bittech.tangshianalyze.crawler.common.DataSet;
import org.springframework.stereotype.Component;

/**
 * Author: Cottonrose
 * Created: 2018/12/11
 */
@Component
public class ConsolePipeline implements Pipeline {
    
    @Override
    public void process(DataSet dataSet) {
        System.out.println(dataSet.getData());
    }
}
