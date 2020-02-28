package com.ti4oss.cmd.command.impl.goods;

import com.ti4oss.cmd.command.Subject;
import com.ti4oss.cmd.command.annotation.AdminCommand;
import com.ti4oss.cmd.command.annotation.CommandMeta;
import com.ti4oss.cmd.command.annotation.CustomerCommand;
import com.ti4oss.cmd.command.impl.AbstractCommand;
import com.ti4oss.entity.Goods;

import java.util.List;

/**
 * Author: Cottonrose
 * Created: 2019/5/17
 */
@CommandMeta(
        name = "LLSP",
        desc = "浏览商品",
        group = "商品信息"
)
@AdminCommand
@CustomerCommand
public class GoodsBrowseCommand extends AbstractCommand {
    
    @Override
    public void execute(Subject subject) {
        List<Goods> goodsList = this.goodsService.queryAllGoods();
        if (goodsList.isEmpty()) {
            warningPrintln("没有任何商品");
        } else {
            for (Goods goods : goodsList) {
                System.out.println(goods);
            }
        }
    }
}
