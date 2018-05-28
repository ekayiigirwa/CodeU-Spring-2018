package codeu.controller;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.Activity;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.*;

/** Servlet class responsible for the Activity Feed page. */
public class ActivityFeedServlet extends HttpServlet {
	
	/** Store class that gives access to Users. */
	private UserStore userStore;
	  
	/** Store class that gives access to Conversations. */
	private ConversationStore conversationStore;

	/** Store class that gives access to Messages. */
	private MessageStore messageStore;

	 /**
	  * Set up state for handling login-related requests. This method is only called when running in a
	  * server, not when running in a test.
	 */
	@Override
	public void init() throws ServletException {
       super.init();
       setConversationStore(ConversationStore.getInstance());
       setMessageStore(MessageStore.getInstance());
	   setUserStore(UserStore.getInstance());
	 }
	  
	/**
	 * Sets the ConversationStore used by this servlet. This function provides a common setup method
	 * for use by the test framework or the servlet's init() function.
	 */
	void setConversationStore(ConversationStore conversationStore) {
	  this.conversationStore = conversationStore;
	}

	/**
	 * Sets the MessageStore used by this servlet. This function provides a common setup method for
	 * use by the test framework or the servlet's init() function.
	 */
	void setMessageStore(MessageStore messageStore) {
	  this.messageStore = messageStore;
	}
	  
	/**
	 * Sets the UserStore used by this servlet. This function provides a common setup method for use
	 * by the test framework or the servlet's init() function.
	 */
	void setUserStore(UserStore userStore) {
	  this.userStore = userStore;
	}

	/**
     * This function fires when a user requests the /activityfeed URL. It simply forwards the request to
	 * activityfeed.jsp.
	 */
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
	   List<Conversation> conversations = conversationStore.getInstance().getAllConversations();
	   request.setAttribute("conversations", conversations);
	   request.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp").forward(request, response);
	  
   }
  

}

