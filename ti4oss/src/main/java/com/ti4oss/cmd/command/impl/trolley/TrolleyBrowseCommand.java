package com.ti4oss.cmd.command.impl.trolley;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.dto.TrolleyGoods;
import com.ti4oss.entity.Goods;
import com.ti4oss.entity.Trolley;

import java.util.List;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "CKGWC",
        desc = "查看购物车",
        group = "我的购物车"
)
@CustomerCommand
public class TrolleyBrowseCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        List<TrolleyGoods> trolleyGoodsList = orderService.queryTrolley();
        if (trolleyGoodsList.isEmpty()) {
            warningPrintln("购物车为空");
        } else {
            System.out.println("【购物车内容】********************************");
            System.out.println("编号  商品名称    商品描述   商品价格(元)   商品折扣    购买数量   商品库存");
            StringBuilder sb = new StringBuilder();
            for (TrolleyGoods trolleyGoods : trolleyGoodsList) {
                Goods goods = trolleyGoods.getGoods();
                Trolley trolley = trolleyGoods.getTrolley();
                sb.append(trolley.getId()).append("  ")
                        .append(goods.getName()).append("  ")
                        .append(goods.getIntroduce()).append("   ")
                        .append(String.format("%.2f", 1.00D * goods.getPrice() / 100)).append("   ")
                        .append(goods.getDiscount()).append("%").append("  ")
                        .append(trolley.getGoodsNum()).append("(").append(goods.getUnit()).append(")").append("   ")
                        .append(goods.getStock()).append("(").append(goods.getUnit()).append(")").append("   ")
                        .append("\n");
            }
            System.out.println(sb.toString());
            
        }
    }
}
