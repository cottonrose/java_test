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
        name = "GXSP",
        desc = "更新商品",
        group = "商品信息"
)
@AdminCommand
public class GoodsUpdateCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        hitPrintln("请输入更新商品编号：");
        int goodsId = scanner.nextInt();
        Goods goods = this.goodsService.getGoods(goodsId);
        if (goods == null) {
            warningPrintln("商品不存在");
        } else {
            System.out.println("商品原信息如下：");
            System.out.println(goods);
            hitPrintln("请输入商品新简介：");
            String introduce = scanner.next();
            hitPrintln("请输入商品新库存（数量）：");
            int stock = scanner.nextInt();
            hitPrintln("请输入商品新单价（单位：元）");
            double priceDouble = scanner.nextDouble();
            int price = new Double(100 * priceDouble).intValue();
            hitPrintln("请输入商品新折扣（范围：[0,100] 比如：75表示75折）: ");
            int discount = scanner.nextInt();
            hitPrintln("确认是否更新（y/n yes/no）: ");
            String confirm = scanner.next();
            if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
                goods.setIntroduce(introduce);
                goods.setStock(stock);
                goods.setPrice(price);
                goods.setDiscount(discount);
                boolean effect = this.goodsService.updateGoods(goods);
                if (effect) {
                    infoPrintln("商品成功更新");
                } else {
                    errorPrintln("商品更新失败，稍后重试");
                }
            }
        }
    }
}
