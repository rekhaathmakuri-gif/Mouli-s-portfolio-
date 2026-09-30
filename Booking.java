package model;
// MODEL: plain data object
public class Booking {
  public int id, userId; public String vehicleType, pickup, dropoff, status;
  public Booking(int userId, String vehicleType, String pickup, String dropoff){
    this.userId=userId; this.vehicleType=vehicleType; this.pickup=pickup; this.dropoff=dropoff;
  }
}
