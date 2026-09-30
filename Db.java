package dao;
import java.sql.*;
public class Db {
  static final String URL="jdbc:mysql://localhost:3306/transport_db";
  static final String USER="root", PASS="your_password";
  public static Connection get() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
  }
}
