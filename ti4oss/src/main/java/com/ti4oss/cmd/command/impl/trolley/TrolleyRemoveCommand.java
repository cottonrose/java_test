package com.ti4oss.cmd.command.impl.trolley;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.entity.Trolley;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "YCSP",
        desc = "移除商品",
        group = "我的购物车"
)
@CustomerCommand
public class TrolleyRemoveCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        hitPrintln("请输入购物车记录编号：");
        int id = scanner.nextInt();
        Trolley trolley = orderService.getTrolley(id);
        if (trolley == null) {
            warningPrintln("购物车记录不存在");
        } else {
            boolean effect;
            hitPrintln("是否移除该记录(y/n yes/no)：");
            String confirm = scanner.next();
            if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
                effect = orderService.deleteTrolley(id);
            } else {
                hitPrintln("请输入移除该记录商量数量（当前是：" + trolley.getGoodsNum() + ") :");
                int goodsNum = scanner.nextInt();
                goodsNum = goodsNum <= 0 ? 0 : goodsNum;
                if (goodsNum >= trolley.getGoodsNum()) {
                    effect = orderService.deleteTrolley(id);
                } else {
                    trolley.setGoodsNum(trolley.getGoodsNum() - goodsNum);
                    effect = orderService.updateTrolley(trolley);
                }
            }
            if (effect) {
                infoPrintln("商品移除成功");
            } else {
                errorPrintln("商品移除失败，稍后重试");
            }
        }
    }
}
