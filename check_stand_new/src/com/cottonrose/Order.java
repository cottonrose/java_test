package com.cottonrose;
import com.cottonrose.GoodsCenter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Auther:cottonrose
 * Created: ${date}
 */
public class Order {
    //订单Order对象创建完成之后，订单编号不能修改
    private final String orderId;
    //订单Order对象创建完成之后，实例化
    private final Map<String,Integer> goodsInfo = new HashMap<>();

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public Map<String, Integer> getGoodsInfo() {
        return goodsInfo;
    }


    //添加商品
    public void add(String goodsId, Integer count){
        int sum;
        if(goodsInfo.containsKey(goodsId)){
            //如果订单中存在该商品编号，总和等于当前编号的数量加上count
            sum = goodsInfo.get(goodsId)+count;
        }else{
            sum = count;
        }
        this.goodsInfo.put(goodsId,sum);
    }
    //取消商品指定数量
    public void cancel(String goodsId,Integer count){
        //获取当前商品数量
        int sum = goodsInfo.get(goodsId);
        //当前商品数量减去取消的商品数量如果小于等于0，该商品数量为0
        sum = (sum-count)<=0?0:(sum-count);
        this.goodsInfo.put(goodsId,sum);
    }
    //清空订单商品
    public void clear(){
        //TODO

    }

}
