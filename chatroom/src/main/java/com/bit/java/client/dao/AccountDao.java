package com.bit.java.client.dao;

import com.bit.java.client.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

/**
 * @program: chatroom
 * @description:实体类
 * @author: Cottonrose
 * @create: 2019-08-16 15:32
 */
public class AccountDao extends BaseDao{
    //注册方法 insert
    public boolean userReg(User user){
        Connection connection = null;
        PreparedStatement statement = null; //表示预编译的sql语句的对象
        try {
            connection = getConnection();
            String sql = "insert into user(username, password, brief)"+
                    " values (?,?,?)";
            statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); //创建一个默认的 PreparedStatement对象，该对象具有检索自动生成的密钥的能力。常数表示生成的密钥应该可用于检索。
            //将指定的参数设置为给定的Java String值。
            statement.setString(1,user.getUsername());
            statement.setString(2,DigestUtils.md5Hex(user.getPassword()));
            statement.setString(3,user.getBrief());
            int row = statement.executeUpdate();
            if (row == 1)
                return true;
        } catch (SQLException e) {
            System.err.println("用户注册失败");
            e.printStackTrace();
        }finally {
            closeResource(connection,statement);
        }
        return false;
    }
    //登陆
    public User userLogin(String userName,String passWord){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            String sql = "select * from user where username = ? and password = ?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            statement.setString(2,DigestUtils.md5Hex(passWord));
            resultSet = statement.executeQuery(); //执行给定的SQL语句，该语句返回单个 ResultSet对象。
            if (resultSet.next()){
                User user = getUser(resultSet);
                return user;
            }
        } catch (SQLException e) {
            System.err.println("用户登陆失败");
            e.printStackTrace();
        }finally {
            closeResource(connection,statement,resultSet);
        }
        return null;
    }
    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setBrief(resultSet.getString("brief"));
        return user;
    }
}