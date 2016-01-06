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
import javax.servlet.http.HttpSession;

public class LoginDao {

    public static boolean validate(String email, String pass, String type) {
        
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
            pst = conn.prepareStatement("select userID from Contacts where email=?");
            pst.setString(1, email);
            rs = pst.executeQuery();
            rs.next();
            int userID = rs.getInt("userID");
                        
            pst = conn.prepareStatement("select * from Users where ID=? and password=? and type=?");
            pst.setInt(1, userID);
            pst.setString(2, pass);
            pst.setString(3, type);
            rs = pst.executeQuery();
            status = rs.next();
                        
            pst = conn.prepareStatement("UPDATE Users SET lastLogin=NOW() WHERE ID=?");
            pst.setLong(1, userID);
            pst.executeUpdate();
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
