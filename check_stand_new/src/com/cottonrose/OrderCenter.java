package com.cottonrose;

/**
 * Auther:cottonrose
 * Created: ${date}
 */
interface OrderCenter {
    void addOrder(Order order);
    void removeOrder(Order orderId);
    String ordersTable();
    String orderTable(String orderId);
    void  storeOrders();
    void loadOrders();
}
