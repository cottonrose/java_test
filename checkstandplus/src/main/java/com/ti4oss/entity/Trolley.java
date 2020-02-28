package com.ti4oss.entity;

import lombok.Data;

/**
 * 购物车信息
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@Data
public class Trolley {
    private Integer id;
    private Integer accountId;
    private Integer goodsId;
    private Integer goodsNum;
}
