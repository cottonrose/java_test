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
        name = "SJSP",
        desc = "上架商品",
        group = "商品信息"
)
@AdminCommand
public class GoodsPutAwayCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        hitPrintln("请输入商品名称：");
        String name = scanner.nextLine();
        hitPrintln("请输入商品简介：");
        String introduce = scanner.nextLine();
        hitPrintln("请输入商品单位（比如：个，打，包，盒，袋，瓶，本，台，千克，箱）：");
        String unit = scanner.nextLine();
        hitPrintln("请输入商品库存（数量）：");
        int stock = scanner.nextInt();
        hitPrintln("请输入商品单价（单位：元）");
        int price = new Double(100 * scanner.nextDouble()).intValue();
        hitPrintln("请输入商品折扣（范围：[0,100] 比如：75表示75折）: ");
        int discount = scanner.nextInt();
        Goods goods = new Goods();
        goods.setName(name);
        goods.setIntroduce(introduce);
        goods.setUnit(unit);
        goods.setStock(stock);
        goods.setPrice(price);
        goods.setDiscount(discount);
        
        boolean effect = this.goodsService.putAwayGoods(goods);
        if (effect) {
            infoPrintln("商品上架成功");
        } else {
            errorPrintln("商品上架失败");
        }
    }
}
