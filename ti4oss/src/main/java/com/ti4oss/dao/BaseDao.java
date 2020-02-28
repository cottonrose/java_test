package com.ti4oss.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Cottonrose
 * Created: 2019/5/10
 */
public class BaseDao {
    
    private static volatile DataSource dataSource;
    
    private static final Map<String, String> SQL_CACHE = new ConcurrentHashMap<>();
    
    private DataSource getDataSource() {
        if (dataSource == null) {
            synchronized(DataSource.class) {
                if (dataSource == null) {
                    dataSource = new MysqlDataSource();
                    String host = System.getProperty("host", "127.0.0.1");
                    String port = System.getProperty("port", "3306");
                    ((MysqlDataSource) dataSource).setUrl("jdbc:mysql://" + (host + ":" + port) + "/check_stand?useSSL=false");
                    ((MysqlDataSource) dataSource).setUser(System.getProperty("user", "root"));
                    ((MysqlDataSource) dataSource).setPassword(System.getProperty("password", "root"));
                }
            }
        }
        return dataSource;
    }
    
    protected Connection getConnection(boolean autoCommit) throws SQLException {
        Connection connection = this.getDataSource().getConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }
    
    protected void closeResource(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        //结果 -> 命令 -> 连接
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public String getSql(String sqlName) {
        return Optional.ofNullable(SQL_CACHE.get(sqlName)).orElseGet(() -> {
            try (InputStream in = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("script/" + sqlName.substring(1) + ".sql");
            ) {
                if (in == null) {
                    throw new RuntimeException("load sql " + sqlName + " failed");
                } else {
                    try (InputStreamReader isr = new InputStreamReader(in);
                         BufferedReader reader = new BufferedReader(isr);) {
                        String value = reader.lines()
                                .reduce((s1, s2) -> s1 + " " + s2)
                                .orElseThrow(() -> new RuntimeException("load sql " + sqlName + " failed"));
                        SQL_CACHE.put(sqlName, value);
                        return value;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("load sql " + sqlName + " failed");
            }
        });
    }
}