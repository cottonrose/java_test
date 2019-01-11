package com.cottonrose;

/**
 * Auther:cottonrose
 * Created: ${date}
 */
public interface GoodsCenter {

    void addGoods(Goods goods);
    void remove(String goodsId);
    void upDateGoods(Goods goods);
    boolean isExistGoods(String goods);
    Goods getGoods(String goodsId);
    String listGoods();
    void store();
    void load();
}
