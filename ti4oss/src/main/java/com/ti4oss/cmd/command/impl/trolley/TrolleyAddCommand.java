package com.ti4oss.cmd.command.impl.trolley;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.entity.Goods;
import com.ti4oss.entity.Trolley;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "TJSP",
        desc = "添加商品",
        group = "我的购物车"
)
@CustomerCommand
public class TrolleyAddCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        hitPrintln("请输入商品编号：");
        int goodsId = scanner.nextInt();
        Goods goods = goodsService.getGoods(goodsId);
        if (goods == null) {
            warningPrintln("商品不存在");
        } else {
            hitPrintln("请输入数量：");
            int goodsNum = Integer.parseInt(scanner.next());
            Trolley trolley = new Trolley();
            trolley.setGoodsId(goodsId);
            trolley.setGoodsNum(goodsNum);
            trolley.setAccountId(subject.getAccount().getId());
            boolean effect = orderService.putTrolley(trolley);
            if (effect) {
                infoPrintln("添加购物车成功");
            } else {
                errorPrintln("添加购物车失败，稍后重试");
            }
        }
    }
}
