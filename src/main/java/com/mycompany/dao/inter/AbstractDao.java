package com.mycompany.dao.inter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDao {
    public Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/resume";
        String username = "root";
        String password = "23042002";
        return DriverManager.getConnection(url, username, password);
    }
}