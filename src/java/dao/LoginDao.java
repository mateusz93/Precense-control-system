package dao;
/**
 *
 * @author Mateusz Wieczorek
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {

    public static boolean validate(String name, String pass, String type) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            pst = conn.prepareStatement("select * from Users where login=? and password=? and type=?");
            pst.setString(1, name);
            pst.setString(2, pass);
            pst.setString(3, type);
            rs = pst.executeQuery();
            status = rs.next();
                        
            PreparedStatement pst2 = null;
            System.out.println();
            pst2 = conn.prepareStatement("UPDATE Users SET lastLogin=NOW() WHERE ID=?");
            pst2.setLong(1, rs.getLong("ID"));
            pst2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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
