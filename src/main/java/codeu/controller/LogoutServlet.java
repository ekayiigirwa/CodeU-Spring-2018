// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.Logout;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

/** Servlet class responsible for the logout page. */
public class LogoutServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /**
   * Set up state for handling logout-related requests. This method is only called when running in a
   * server, not when running in a test.
   */
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

  /**
   * This function fires when a user requests the /logout URL. It simply forwards the request to
   * logout.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/logout.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the logout form.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
	  String button = request.getParameter("button");
	  
      if ("Yes".equals(button)) {
    	  User user = userStore.getUser((String) request.getSession().getAttribute("user"));
    	  Instant logout = Instant.now();
    	  Logout time = new Logout(logout, user.getName());
    	  user.getLogoutArr().add(time);
    	  request.getSession().setAttribute("logout", user.getLogoutArr());
    	  request.logout();
    	  request.getSession().setAttribute("user", null);
    	  userStore.updateUser(user);
    	  response.sendRedirect("/login");
      } 
      if ("No".equals(button)) {
    	  response.sendRedirect("/activityfeed");
      } 
  }
}
