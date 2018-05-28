
//If you forget to add this package, a 403 error
//is passed therefore, DO NOT REMOVE!!!
package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {



	@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
      	//points directly to the admin.jsp file that holds the U.I
    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }
  

}