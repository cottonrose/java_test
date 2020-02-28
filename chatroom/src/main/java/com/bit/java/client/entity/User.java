package com.bit.java.client.entity;

/**
 * @program: chatroom
 * @description:实体类
 * @author: Cottonrose
 * @create: 2019-08-16 15:38
 */
public class User {
        private Integer id;
        private String username;
        private String password;
        private String brief;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", brief='" + brief + '\'' +
                '}';
    }
}