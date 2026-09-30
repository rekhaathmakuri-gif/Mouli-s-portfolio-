package dao;
import model.Booking;
import java.sql.*;
// DAO: all JDBC lives here (PreparedStatement prevents SQL injection)
public class BookingDao {
  public int save(Booking b) throws SQLException {
    String sql="INSERT INTO bookings(user_id,vehicle_type,pickup,dropoff) VALUES(?,?,?,?)";
    try(Connection c=Db.get(); PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
      p.setInt(1,b.userId); p.setString(2,b.vehicleType);
      p.setString(3,b.pickup); p.setString(4,b.dropoff);
      p.executeUpdate();
      ResultSet k=p.getGeneratedKeys(); return k.next()?k.getInt(1):-1;
    }
  }
  public void saveLocation(int bookingId,double lat,double lng) throws SQLException {
    String sql="INSERT INTO locations(booking_id,lat,lng) VALUES(?,?,?) "
             +"ON DUPLICATE KEY UPDATE lat=VALUES(lat), lng=VALUES(lng)";
    try(Connection c=Db.get(); PreparedStatement p=c.prepareStatement(sql)){
      p.setInt(1,bookingId); p.setDouble(2,lat); p.setDouble(3,lng);
      p.executeUpdate();
    }
  }
  public double[] getLocation(int bookingId) throws SQLException {
    String sql="SELECT lat,lng FROM locations WHERE booking_id=?";
    try(Connection c=Db.get(); PreparedStatement p=c.prepareStatement(sql)){
      p.setInt(1,bookingId); ResultSet r=p.executeQuery();
      return r.next()? new double[]{r.getDouble(1),r.getDouble(2)} : null;
    }
  }
}
