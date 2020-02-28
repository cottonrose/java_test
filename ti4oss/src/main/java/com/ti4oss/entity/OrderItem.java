package com.ti4oss.entity;

import lombok.Data;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@Data
public class OrderItem {
    
    private Integer id;
    private String orderId;
    private Integer goodsId;
    private String goodsName;
    private String goodsIntroduce;
    private Integer goodsNum;
    private String goodsUnit;
    private Integer goodsPrice;
    private Integer goodsDiscount;
}
