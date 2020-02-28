package com.ti4oss.cmd.command.impl.order;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.common.OrderStatus;
import com.ti4oss.entity.Order;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "QXDD",
        desc = "取消订单",
        group = "我的订单"
)
@CustomerCommand
public class OrderCancelCommand extends AbstractCommand {
    
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
            hitPrintln("是否继续取消(y/n yes/no)：");
            String confirm = scanner.nextLine();
            if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
                boolean effect = this.orderService.cancelOrder(order);
                if (effect) {
                    infoPrintln("完成待支付订单取消");
                } else {
                    errorPrintln("待支付订单取消失败，稍后请重试");
                }
            } else {
                infoPrintln("已取消执行，稍后可以继续操作");
            }
        } else {
            warningPrintln("已经支付过的订单，不能取消");
        }
    }
}
