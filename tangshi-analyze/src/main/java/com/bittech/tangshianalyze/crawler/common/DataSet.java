package com.bittech.tangshianalyze.crawler.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Cottonrose
 * Created: 2018/12/11
 */
public class DataSet {
    
    private Map<String, Object> data = new HashMap<>();
    
    public Object getData(String key) {
        return data.get(key);
    }
    
    public void putData(String key, Object value) {
        data.put(key, value);
    }
    
    public Map<String, Object> getData() {
        return new HashMap<>(data);
    }
    
}
