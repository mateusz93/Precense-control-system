/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mateusz
 */
public class RegisterDao {

    public static boolean accept(String firstName, String lastName, String type, String email, String username, String password, String confirmPassword) {
        
        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String pass = "root";
        
        if (!password.equals(confirmPassword)) {
            return false;
        }
        
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, pass);
            pst = conn.prepareStatement("insert into Users (login, firstName, lastName, type, password) values (?, ?, ?, ?, ?)");
            pst.setString(1, username);
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, type);
            pst.setString(5, password);
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }
}
