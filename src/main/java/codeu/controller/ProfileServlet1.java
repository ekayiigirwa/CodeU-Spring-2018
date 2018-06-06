package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

/** Servlet class responsible for the Profile page. */
public class ProfileServlet1 extends HttpServlet {


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
         request.getRequestDispatcher("/WEB-INF/view/Profile1.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        // gets the profile edits
        String profileUpdate = request.getParameter("AboutMe");
        // print them on the browser
        response.getOutputStream().println(profileUpdate);
        //System.out.println(profileUpdate);
        
    
  }
}
