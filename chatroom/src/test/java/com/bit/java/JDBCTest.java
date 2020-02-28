package com.bit.java;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.bit.java.util.CommUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

public class JDBCTest {
    //加载DataSource
    private static DruidDataSource dataSource;
    static {
        Properties proper = CommUtils.
                loadProperties("datasource.properties");
        try {
            dataSource = (DruidDataSource) DruidDataSourceFactory.
                    createDataSource(proper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    //查询
    public void testQuery(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            //获取连接
            connection = (Connection) dataSource.getPooledConnection();
            String sql = "select * from user";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String passWord = resultSet.getString("password");
                String brief = resultSet.getString("brief");
                System.out.println("id为："+id+",用户名为："+userName+"，密码为："
                +passWord+"，简介为："+brief);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResource(connection,statement,resultSet);
        }
    }

    @Test
    //更新
    public void testInsert(){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = (Connection) dataSource.getPooledConnection();
            String pwd = DigestUtils.md5Hex("123");
            String sql = "insert into user (username, password, brief) " +
                    "values (?,?,?)";
            statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
             statement.setString(1,"test1");
             statement.setString(2,pwd);
             statement.setString(3,"还是帅");
            int rows =  statement.executeUpdate();
            Assert.assertEquals(1,rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResource(connection,statement);
        }
    }

    @Test
    public void testLogin(){
        String userName = "test2";
        String password = "12311";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = (Connection)dataSource.getPooledConnection();
            String sql = "select * from user where username = '"+userName+"'"+
                    "and password = '"+password+"'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                System.out.println("登陆成功");
            }else {
                System.out.println("登陆失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //关闭资源
    public  static void closeResource(Connection connection, Statement statement){
       if (connection!=null){
           try {
               connection.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       if (statement != null){
           try {
               statement.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
    }
    public void closeResource(Connection connection,Statement statement,ResultSet resultSet){
        closeResource(connection,statement);
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
