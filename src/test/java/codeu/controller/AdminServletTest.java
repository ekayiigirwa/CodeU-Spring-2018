package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;

public class AdminServletTest {

  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;
  private UserStore mockUserStore;
  private MessageStore mockMessageStore;

  @Before
  public void setup() throws IOException {
    adminServlet = new AdminServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
    .thenReturn(mockRequestDispatcher);

    mockConversationStore = Mockito.mock(ConversationStore.class);
    adminServlet.setConversationStore(mockConversationStore);

    mockUserStore = Mockito.mock(UserStore.class);
    adminServlet.setUserStore(mockUserStore);

    mockMessageStore = Mockito.mock(MessageStore.class);
    adminServlet.setMessageStore(mockMessageStore);
  }
  
 @Test
  public void testDoGet() throws IOException, ServletException {

    Mockito.when(mockUserStore.count()).thenReturn(2);
    Mockito.when(mockConversationStore.count()).thenReturn(2);
    Mockito.when(mockMessageStore.count()).thenReturn(2);


    adminServlet.doGet(mockRequest, mockResponse);


    Mockito.verify(mockRequest).setAttribute("numberOfUsers", mockUserStore.count());
    Mockito.verify(mockRequest).setAttribute("numberOfConversations", mockConversationStore.count());
    Mockito.verify(mockRequest).setAttribute("numberOfMessages", mockMessageStore.count());


    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);

  }
  
}
