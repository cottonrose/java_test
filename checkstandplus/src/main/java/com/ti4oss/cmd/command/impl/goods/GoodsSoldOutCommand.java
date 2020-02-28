package com.ti4oss.cmd.command.impl.goods;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.AdminCommand;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.entity.Goods;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "XJSP",
        desc = "下架商品",
        group = "商品信息"
)
@AdminCommand
public class GoodsSoldOutCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        hitPrintln("请输入下架商品编号：");
        int goodsId = scanner.nextInt();
        Goods goods = this.goodsService.getGoods(goodsId);
        if (goods == null) {
            warningPrintln("下架商品不存在");
        } else {
            System.out.println("下架商品如下：");
            System.out.println(goods);
            hitPrintln("确认是否下架（y/n yes/no）: ");
            String confirm = scanner.next();
            if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
                boolean effect = this.goodsService.soldOutGoods(goodsId);
                if (effect) {
                    infoPrintln("商品成功下架");
                } else {
                    errorPrintln("商品下架失败，稍后重试");
                }
            }
        }
    }
}
