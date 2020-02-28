package com.ti4oss.dao;

import com.ti4oss.common.OrderStatus;
import com.ti4oss.entity.Order;
import com.ti4oss.entity.OrderItem;
import com.ti4oss.entity.Trolley;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
public class OrderDao extends BaseDao {
    
    public boolean insertTrolley(Trolley trolley) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            String sql = "insert into shopping_trolley(account_id, goods_id, goods_num) values (?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, trolley.getAccountId());
            statement.setInt(2, trolley.getGoodsId());
            statement.setInt(3, trolley.getGoodsNum());
            effect = statement.executeUpdate() == 1;
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                trolley.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return effect;
    }
    
    public Trolley queryTrolleyByGoodsId(int goodsId, int accountId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "select id, account_id, goods_id, goods_num from shopping_trolley where goods_id= ? and account_id=? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, goodsId);
            statement.setInt(2, accountId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractTrolley(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return null;
    }
    
    public Trolley queryTrolleyById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "select id, account_id, goods_id, goods_num from shopping_trolley where id= ? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractTrolley(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return null;
    }
    
    public List<Trolley> queryTrolley() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Trolley> trolleyList = new ArrayList<>();
        try {
            connection = this.getConnection(true);
            String sql = "select id, account_id, goods_id, goods_num from shopping_trolley";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trolleyList.add(this.extractTrolley(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return trolleyList;
    }
    
    public boolean deleteTrolley(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            String sql = "delete from shopping_trolley where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            effect = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return effect;
    }
    
    public boolean deleteTrolley(List<Integer> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            StringBuilder sql = new StringBuilder("delete from shopping_trolley where id in(");
            for (Integer id : ids) {
                sql.append(id).append(",");
            }
            sql.setLength(sql.length() - 1);
            sql.append(")");
            statement = connection.prepareStatement(sql.toString());
            effect = statement.executeUpdate() == ids.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return effect;
    }
    
    public boolean modifyTrolley(Trolley trolley) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            String sql = "update  shopping_trolley set goods_num =?  where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, trolley.getGoodsNum());
            statement.setInt(2, trolley.getId());
            effect = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(null, statement, connection);
        }
        return effect;
    }
    
    private Trolley extractTrolley(ResultSet resultSet) throws SQLException {
        Trolley trolley = new Trolley();
        trolley.setId(resultSet.getInt("id"));
        trolley.setAccountId(resultSet.getInt("account_id"));
        trolley.setGoodsId(resultSet.getInt("goods_id"));
        trolley.setGoodsNum(resultSet.getInt("goods_num"));
        return trolley;
    }
    
    public boolean insertOrder(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        String insertOrderSql = "insert into `order`(id, account_id, create_time, finish_time, actual_amount, total_money, order_status, account_name) values (?,?,?,?,?,?,?,?)";
        String insertOrderItemSql = "insert into order_item(order_id, goods_id, goods_name, goods_introduce, goods_num, goods_unit, goods_price, goods_discount) VALUES (?,?,?,?,?,?,?,?)";
        boolean effect = false;
        try {
            connection = this.getConnection(false);
            statement = connection.prepareStatement(insertOrderSql);
            statement.setString(1, order.getId());
            statement.setInt(2, order.getAccountId());
            statement.setTimestamp(3, Timestamp.valueOf(order.getCreateTime()));
            statement.setTimestamp(4, order.getFinishTime() == null ? null : Timestamp.valueOf(order.getCreateTime()));
            statement.setInt(5, order.getActualAmount());
            statement.setInt(6, order.getTotalMoney());
            statement.setInt(7, order.getOrderStatus().getFlag());
            statement.setString(8, order.getAccountName());
            effect = statement.executeUpdate() == 1;
            statement = connection.prepareStatement(insertOrderItemSql);
            for (OrderItem orderItem : order.getOrderItemList()) {
                statement.setString(1, orderItem.getOrderId());
                statement.setInt(2, orderItem.getGoodsId());
                statement.setString(3, orderItem.getGoodsName());
                statement.setString(4, orderItem.getGoodsIntroduce());
                statement.setInt(5, orderItem.getGoodsNum());
                statement.setString(6, orderItem.getGoodsUnit());
                statement.setInt(7, orderItem.getGoodsPrice());
                statement.setInt(8, orderItem.getGoodsDiscount());
                statement.addBatch();
            }
            int[] effects = statement.executeBatch();
            for (int i : effects) {
                effect = i == 1;
            }
            if (effect) {
                connection.commit();
            } else {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            this.closeResource(null, statement, connection);
        }
        
        return effect;
    }
    
    public boolean deleteOrder(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        String deleteOrder = "delete from `order` where id =?";
        String deleteOrderItem = "delete from  order_item where order_id=?";
        boolean effect = false;
        try {
            connection = this.getConnection(false);
            statement = connection.prepareStatement(deleteOrder);
            statement.setString(1, order.getId());
            effect = statement.executeUpdate() == 1;
            statement = connection.prepareStatement(deleteOrderItem);
            statement.setString(1, order.getId());
            effect = statement.executeUpdate() == order.getOrderItemList().size();
            if (effect) {
                connection.commit();
            } else {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            this.closeResource(null, statement, connection);
        }
        return effect;
    }
    
    public List<Order> queryOrderByAccount(Integer accountId) {
        List<Order> orderList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = this.getSql("@query_order_by_account");
            statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            resultSet = statement.executeQuery();
            Order order = null;
            while (resultSet.next()) {
                if (order == null) {
                    order = new Order();
                    this.extractOrder(order, resultSet);
                    orderList.add(order);
                }
                String orderId = resultSet.getString("order_id");
                if (!orderId.equals(order.getId())) {
                    order = new Order();
                    this.extractOrder(order, resultSet);
                    orderList.add(order);
                }
                OrderItem orderItem = this.extractOrderItem(resultSet);
                order.getOrderItemList().add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return orderList;
    }
    
    private OrderItem extractOrderItem(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getInt("item_id"));
        orderItem.setGoodsId(resultSet.getInt("goods_id"));
        orderItem.setGoodsName(resultSet.getString("goods_name"));
        orderItem.setGoodsIntroduce(resultSet.getString("goods_introduce"));
        orderItem.setGoodsNum(resultSet.getInt("goods_num"));
        orderItem.setGoodsUnit(resultSet.getString("goods_unit"));
        orderItem.setGoodsPrice(resultSet.getInt("goods_price"));
        orderItem.setGoodsDiscount(resultSet.getInt("goods_discount"));
        return orderItem;
    }
    
    private void extractOrder(final Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getString("order_id"));
        order.setAccountId(resultSet.getInt("account_id"));
        order.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
        Timestamp finishTime = resultSet.getTimestamp("finish_time");
        if (finishTime != null) {
            order.setFinishTime(finishTime.toLocalDateTime());
        }
        order.setActualAmount(resultSet.getInt("actual_amount"));
        order.setTotalMoney(resultSet.getInt("total_money"));
        order.setOrderStatus(OrderStatus.valueOf(resultSet.getInt("order_status")));
        order.setAccountName(resultSet.getString("account_name"));
    }
    
    public Order queryOrder(String orderId) {
        Order order = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = this.getSql("@query_order_by_id");
            statement = connection.prepareStatement(sql);
            statement.setString(1, orderId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (order == null || order.getId() == null) {
                    order = new Order();
                    this.extractOrder(order, resultSet);
                }
                OrderItem orderItem = this.extractOrderItem(resultSet);
                order.getOrderItemList().add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return order;
    }
    
    public boolean updateOrder(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        String updateOrder = "update `order` set finish_time=?, order_status =? where id=?";
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            statement = connection.prepareStatement(updateOrder);
            statement.setTimestamp(1, Timestamp.valueOf(order.getFinishTime()));
            statement.setInt(2, order.getOrderStatus().getFlag());
            statement.setString(3, order.getId());
            effect = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            this.closeResource(null, statement, connection);
        }
        return effect;
    }
}