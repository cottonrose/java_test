package com.ti4oss.service;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.common.OrderStatus;
import com.ti4oss.dao.BaseDao;
import com.ti4oss.dao.OrderDao;
import com.ti4oss.dto.TrolleyGoods;
import com.ti4oss.entity.Goods;
import com.ti4oss.entity.Order;
import com.ti4oss.entity.OrderItem;
import com.ti4oss.entity.Trolley;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
public class OrderService extends BaseDao {
    
    
    private GoodsService goodsService;
    
    private OrderDao orderDao;
    
    public OrderService() {
        this.goodsService = new GoodsService();
        this.orderDao = new OrderDao();
    }
    
    
    public boolean putTrolley(Trolley trolley) {
        Trolley oldTrolley = this.orderDao.queryTrolleyByGoodsId(trolley.getGoodsId(), trolley.getAccountId());
        if (oldTrolley == null) {
            return this.orderDao.insertTrolley(trolley);
        } else {
            int num = oldTrolley.getGoodsNum() + trolley.getGoodsNum();
            oldTrolley.setGoodsNum(num);
            return this.updateTrolley(oldTrolley);
        }
    }
    
    public Trolley getTrolley(int id) {
        return this.orderDao.queryTrolleyById(id);
    }
    
    public boolean deleteTrolley(int id) {
        return this.orderDao.deleteTrolley(id);
    }
    
    public boolean updateTrolley(Trolley trolley) {
        return this.orderDao.modifyTrolley(trolley);
    }
    
    public List<TrolleyGoods> queryTrolley() {
        List<TrolleyGoods> list = new ArrayList<>();
        List<Trolley> trolleyList = this.orderDao.queryTrolley();
        List<Goods> goodsList = this.goodsService.queryAllGoods();
        for (Trolley trolley : trolleyList) {
            for (Goods goods : goodsList) {
                if (goods.getId().equals(trolley.getGoodsId())) {
                    list.add(new TrolleyGoods(goods, trolley));
                    break;
                }
            }
        }
        return list;
    }
    
    
    /**
     * 核心逻辑
     * 订单计算
     *
     * @param subject
     * @param trolleyGoodsList
     * @return
     */
    public Order computeOrder(Subject subject, List<TrolleyGoods> trolleyGoodsList) {
        Order order = new Order();
        order.setId(String.valueOf(System.currentTimeMillis()));
        order.setAccountId(subject.getAccount().getId());
        order.setAccountName(subject.getAccount().getUsername());
        order.setCreateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PAYING);
        int totalMoney = 0;
        int actualAmount = 0;
        for (TrolleyGoods trolleyGoods : trolleyGoodsList) {
            Goods goods = trolleyGoods.getGoods();
            Trolley trolley = trolleyGoods.getTrolley();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setGoodsId(goods.getId());
            orderItem.setGoodsName(goods.getName());
            orderItem.setGoodsUnit(goods.getUnit());
            orderItem.setGoodsPrice(goods.getPrice());
            orderItem.setGoodsDiscount(goods.getDiscount());
            orderItem.setGoodsNum(trolley.getGoodsNum());
            orderItem.setGoodsIntroduce(goods.getIntroduce());
            order.getOrderItemList().add(orderItem);
            int currentMoney = goods.getPrice() * trolley.getGoodsNum();
            totalMoney += currentMoney;
            actualAmount += (currentMoney * goods.getDiscount() / 100);
        }
        order.setTotalMoney(totalMoney);
        order.setActualAmount(actualAmount);
        return order;
    }
    
    public boolean commitOrder(Order order, List<Integer> trolleyIds) {
        boolean rs = this.orderDao.insertOrder(order);
        this.orderDao.deleteTrolley(trolleyIds);
        return rs;
    }
    
    public List<Order> queryOrderByAccount(Integer accountId) {
        return this.orderDao.queryOrderByAccount(accountId);
    }
    
    public Order queryOrderById(String orderId) {
        return this.orderDao.queryOrder(orderId);
    }
    
    public boolean payOrder(Order order) {
        return this.orderDao.updateOrder(order);
    }
    
    public boolean cancelOrder(Order order) {
        return this.orderDao.deleteOrder(order);
    }
}