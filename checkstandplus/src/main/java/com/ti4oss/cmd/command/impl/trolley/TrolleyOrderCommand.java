package com.ti4oss.cmd.command.impl.trolley;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.common.OrderStatus;
import com.ti4oss.dto.TrolleyGoods;
import com.ti4oss.entity.Goods;
import com.ti4oss.entity.Order;
import com.ti4oss.entity.Trolley;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "SPXD",
        desc = "商品下单",
        group = "我的购物车"
)
@CustomerCommand
public class TrolleyOrderCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        hitPrintln("请输入购物车记录编号进行下单(多个用英文逗号分割)：");
        String idLine = scanner.nextLine();
        String[] idStrArray = idLine.split(",");
        try {
            List<TrolleyGoods> trolleyGoodsList = this.orderService.queryTrolley();
            List<TrolleyGoods> orderTrolleyGoodsList =
                    Arrays.stream(idStrArray)
                            .map(Integer::parseInt)
                            .collect(Collectors.toSet())
                            .stream()
                            .map(id -> {
                                for (TrolleyGoods trolleyGoods : trolleyGoodsList) {
                                    if (trolleyGoods.getTrolley().getId().equals(id)) {
                                        return trolleyGoods;
                                    }
                                }
                                return null;
                            }).filter(Objects::nonNull)
                            .collect(Collectors.toList());
            
            List<TrolleyGoods> removes = new ArrayList<>();
            Iterator<TrolleyGoods> iterator = orderTrolleyGoodsList.iterator();
            while (iterator.hasNext()) {
                TrolleyGoods trolleyGoods = iterator.next();
                Goods goods = trolleyGoods.getGoods();
                Trolley trolley = trolleyGoods.getTrolley();
                if (goods.getStock() < trolley.getGoodsNum()) {
                    removes.add(trolleyGoods);
                    iterator.remove();
                }
            }
            if (removes.isEmpty()) {
                Order order = this.orderService.computeOrder(subject, orderTrolleyGoodsList);
                System.out.println(order);
                hitPrintln("是否继续支付(y/n yes/no)：");
                String confirm = scanner.nextLine();
                if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
                    order.setFinishTime(LocalDateTime.now());
                    order.setOrderStatus(OrderStatus.OK);
                }
                //清空购物车中选择的记录
                List<Integer> trolleyIds = orderTrolleyGoodsList
                        .stream()
                        .map(trolleyGoods -> trolleyGoods.getTrolley().getId())
                        .collect(Collectors.toList());
                boolean effect = this.orderService.commitOrder(order, trolleyIds);
                if (effect) {
                    if (order.getOrderStatus() == OrderStatus.OK) {
                        infoPrintln("下单成功");
                    } else {
                        infoPrintln("订单已保存，稍后可以继续支付");
                    }
                } else {
                    errorPrintln("下单失败，稍后重试");
                }
            } else {
                for (TrolleyGoods trolleyGoods : removes) {
                    Goods goods = trolleyGoods.getGoods();
                    warningPrintln("商品编号：" + goods.getId() + " 名称：" + goods.getName() + "库存不足，无法下单");
                }
            }
        } catch (NumberFormatException e) {
            warningPrintln("输入编号不合法，编号是整数类型");
        }
    }
}
