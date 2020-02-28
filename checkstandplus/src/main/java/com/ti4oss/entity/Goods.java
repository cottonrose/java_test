package com.ti4oss.entity;

import lombok.Data;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
@Data
public class Goods {
    
    private Integer id;
    private String name;
    private String introduce;
    private Integer stock;
    private String unit;
    private Integer price;
    private Integer discount;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("【商品信息】").append("\n");
        sb.append("【编号】： ").append(this.getId()).append("\n")
                .append("【名称】： ").append(this.getName()).append("\n")
                .append("【简介】： ").append(this.getIntroduce()).append("\n")
                .append("【库存】： ").append(this.getStock()).append(" (").append(this.getUnit()).append(") ").append("\n")
                .append("【单价】： ").append(String.format("%.2f", 1.00D * this.getPrice() / 100)).append(" (元) ").append("\n")
                .append("【折扣】： ").append(this.getDiscount()).append(" (折) ").append("\n");
        sb.append("***************************************************");
        return sb.toString();
    }

}
