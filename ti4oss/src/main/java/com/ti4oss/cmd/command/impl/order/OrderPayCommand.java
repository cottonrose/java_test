package com.ti4oss.cmd.command.impl.order;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.common.OrderStatus;
import com.ti4oss.entity.Order;

import java.time.LocalDateTime;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "ZFDD",
        desc = "支付订单",
        group = "我的订单"
)
@CustomerCommand
public class OrderPayCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        hitPrintln("请输入订单编号：");
        String orderId = scanner.nextLine();
        Order order = this.orderService.queryOrderById(orderId);
        if (order == null) {
            errorPrintln("输入的订单不存在");
            return;
        }
        if (order.getOrderStatus() == OrderStatus.PAYING) {
            System.out.println(order);
            hitPrintln("是否继续支付(y/n yes/no)：");
            String confirm = scanner.nextLine();
            if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
                order.setFinishTime(LocalDateTime.now());
                order.setOrderStatus(OrderStatus.OK);
                boolean effect = this.orderService.payOrder(order);
                if (effect) {
                    infoPrintln("支付完成");
                } else {
                    errorPrintln("支付失败，稍后请重试");
                }
            } else {
                infoPrintln("已取消支付，稍后可以继续支付");
            }
        } else {
            warningPrintln("输入的订单已经支付过，请不要重复支付");
        }
    }
}
