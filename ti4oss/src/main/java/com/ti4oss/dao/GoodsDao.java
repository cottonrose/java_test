package com.ti4oss.dao;

import com.ti4oss.entity.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Cottonrose
 * Created: 2019/5/9
 */
public class GoodsDao extends BaseDao {
    
    public boolean insertGoods(Goods goods) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean effect = false;
        try {
            String sql = "insert into goods (name,introduce, stock, unit, price, discount) value (?,?,?,?,?,?)";
            connection = this.getConnection(true);
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, goods.getName());
            statement.setString(2, goods.getIntroduce());
            statement.setInt(3, goods.getStock());
            statement.setString(4, goods.getUnit());
            statement.setInt(5, goods.getPrice());
            statement.setInt(6, goods.getDiscount());
            effect = statement.executeUpdate() == 1;
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                goods.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return effect;
    }
    
    public boolean modifyGoods(Goods goods) {
        //名称，简介，价格，折扣，库存
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "update goods set name=? , introduce =?,  stock=? , price=?, discount =? where  id= ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, goods.getName());
            statement.setString(2, goods.getIntroduce());
            statement.setInt(3, goods.getStock());
            statement.setInt(4, goods.getPrice());
            statement.setInt(5, goods.getDiscount());
            statement.setInt(6, goods.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return false;
        
    }
    
    public boolean deleteGoods(int goodsId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "delete  from goods where  id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, goodsId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return false;
    }
    
    public List<Goods> queryGoods() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Goods> goodsArrayList = new ArrayList<>();
        try {
            connection = this.getConnection(true);
            String sql = "select id, name, introduce, stock, unit, price, discount from goods ";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Goods goods = this.extractGoods(resultSet);
                if (goods != null) {
                    goodsArrayList.add(goods);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return goodsArrayList;
    }
    
    
    public Goods queryGoodsById(int goodsId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "select id, name, introduce, stock, unit, price, discount from goods where  id =?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, goodsId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractGoods(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, statement, connection);
        }
        return null;
    }
    
    
    private Goods extractGoods(ResultSet resultSet) throws SQLException {
        Goods goods = new Goods();
        goods.setId(resultSet.getInt("id"));
        goods.setName(resultSet.getString("name"));
        goods.setIntroduce(resultSet.getString("introduce"));
        goods.setStock(resultSet.getInt("stock"));
        goods.setUnit(resultSet.getString("unit"));
        goods.setPrice(resultSet.getInt("price"));
        goods.setDiscount(resultSet.getInt("discount"));
        return goods;
    }
}