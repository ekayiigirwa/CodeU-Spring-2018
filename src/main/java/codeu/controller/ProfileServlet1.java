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

/** Store class that gives access to Users. */
  private UserStore userStore;

  /** Set up state for handling chat requests. */
  @Override
  public void init() throws ServletException {
    super.init();

    setUserStore(UserStore.getInstance());
    }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

        if ((String) request.getSession().getAttribute("user") == null) {
          // user is not logged in, don't let them add a message
            response.sendRedirect("/login");
            return;
        }        
        
        String bioInfo = userStore.getUser((String) request.getSession().getAttribute("user")).getBio();
        request.setAttribute("UserInfo",bioInfo);
        request.getRequestDispatcher("/WEB-INF/view/Profile1.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
         String username = (String) request.getSession().getAttribute("user");
        if (username == null) {
            // user is not logged in, don't let them add a message
            response.sendRedirect("/login");
            return;
        }
        String bio = request.getParameter("AboutMe");
        User myUser = userStore.getUser(username);
        myUser.setBio(bio);
        userStore.updateUser(myUser);
        // gets the profile edits
        String profileUpdate = request.getParameter("AboutMe");
        response.sendRedirect("/profile");
        
    
  }
}
