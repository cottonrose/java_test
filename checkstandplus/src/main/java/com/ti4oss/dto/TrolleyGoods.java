package com.ti4oss.dto;

import com.ti4oss.entity.Goods;
import com.ti4oss.entity.Trolley;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
public class TrolleyGoods {
    
    private final Goods goods;
    
    private final Trolley trolley;
    
    public TrolleyGoods(Goods goods, Trolley trolley) {
        this.goods = goods;
        this.trolley = trolley;
    }
    
    public Goods getGoods() {
        return goods;
    }
    
    public Trolley getTrolley() {
        return trolley;
    }
}
