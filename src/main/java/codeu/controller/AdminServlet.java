
//If you forget to add this package, a 403 error
//is passed therefore, DO NOT REMOVE!!!
package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import java.util.List;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;



/**Gives access to the cloud data storage*/
import codeu.model.store.persistence.PersistentDataStore;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to  Messages */
  private MessageStore messageStore;
 /**
   * Set up state for handling conversation, user and message-related requests. 
   * This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
  }

  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  void setMessageStore(MessageStore messageStore){
    this.messageStore = messageStore;
  }

	@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
 		int numberOfUsers = userStore.count();
 		int numberOfConversations = conversationStore.count();
 		int numberOfMessages = messageStore.count(); 

    String newestUser = userStore.getNewestUser();
    String mostActiveUser = conversationStore.mostActive();
    String wordiestUser = messageStore.wordyUser();

    request.setAttribute("numberOfUsers", numberOfUsers);
    request.setAttribute("numberOfConversations", numberOfConversations);
    request.setAttribute("numberOfMessages", numberOfMessages);

    request.setAttribute("newestUser", newestUser);
    request.setAttribute("mostActiveUser", mostActiveUser);
    request.setAttribute("wordiestUser", wordiestUser);

    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }
  

  
}
