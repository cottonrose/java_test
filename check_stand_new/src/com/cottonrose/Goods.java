package com.cottonrose;

import java.time.LocalDate;

/**
 * Auther:cottonrose
 * Created: ${date}
 */
public class Goods {
    private String id; //商品货号
    private String name; //商品名称
    private double price; //商品价格

    //构造方法
    public Goods(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return String.format("%s %s %.2f",this.getId(),this.getName(),this.getPrice());
    }
}
