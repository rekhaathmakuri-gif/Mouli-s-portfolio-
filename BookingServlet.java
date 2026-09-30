package controller;
import dao.BookingDao; import model.Booking;
import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*;
import java.io.IOException;
// CONTROLLER: receives requests, calls DAO, returns JSON
@WebServlet("/api/*")
public class BookingServlet extends HttpServlet {
  BookingDao dao=new BookingDao();
  protected void doPost(HttpServletRequest q,HttpServletResponse s) throws IOException {
    s.setContentType("application/json");
    try{
      String path=q.getPathInfo();
      if("/book".equals(path)){
        int uid=1; // replace with logged-in user id from session
        int id=dao.save(new Booking(uid,q.getParameter("vehicle"),q.getParameter("pickup"),q.getParameter("dropoff")));
        s.getWriter().print("{\"bookingId\":"+id+"}");
      } else if("/location".equals(path)){ // sender pushes live GPS
        dao.saveLocation(Integer.parseInt(q.getParameter("id")),
          Double.parseDouble(q.getParameter("lat")),Double.parseDouble(q.getParameter("lng")));
        s.getWriter().print("{\"ok\":true}");
      }
    }catch(Exception e){ s.setStatus(500); s.getWriter().print("{\"error\":\"server\"}"); }
  }
  protected void doGet(HttpServletRequest q,HttpServletResponse s) throws IOException {
    s.setContentType("application/json");
    try{ // receiver polls latest location
      double[] l=dao.getLocation(Integer.parseInt(q.getParameter("id")));
      s.getWriter().print(l==null?"{}":"{\"lat\":"+l[0]+",\"lng\":"+l[1]+"}");
    }catch(Exception e){ s.setStatus(500); }
  }
}
