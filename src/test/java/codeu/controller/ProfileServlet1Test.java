package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;

public class ProfileServlet1Test {

    private ProfileServlet1 ProfileServlet1;
    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;
    private UserStore mockUserStore;
    

    @Before
    public void setup() throws IOException, ServletException{
        ProfileServlet1 = new ProfileServlet1();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockSession = Mockito.mock(HttpSession.class);
        Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/Profile1.jsp"))
        .thenReturn(mockRequestDispatcher);

        mockUserStore = Mockito.mock(UserStore.class);
        ProfileServlet1.setUserStore(mockUserStore);

    }

    @Test
    public void testDoGet_UserNotLoggedIn() throws IOException, ServletException {
        Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

        ProfileServlet1.doGet(mockRequest, mockResponse);

        Mockito.verify(mockUserStore, Mockito.never()).updateUser(Mockito.any(User.class));
        Mockito.verify(mockResponse).sendRedirect("/login");
    }

    @Test
    public void testDoGetProfile() throws IOException, ServletException {
       
        Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
        User fakeUser =
            new User(
             UUID.randomUUID(),
                "test_username",
                "$2a$10$bBiLUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
                Instant.now());
        fakeUser.setBio("testBio");
        Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);
        
        ProfileServlet1.doGet(mockRequest, mockResponse);
        Mockito.verify(mockRequest).setAttribute("UserInfo", "testBio");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

    @Test
    public void testDoPost_UserNotLoggedIn() throws IOException, ServletException {
        Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

        ProfileServlet1.doPost(mockRequest, mockResponse);

        Mockito.verify(mockUserStore, Mockito.never()).updateUser(Mockito.any(User.class));
        Mockito.verify(mockResponse).sendRedirect("/login");
    }

    @Test
     public void testDoPost_UpdateUser() throws IOException, ServletException {

        Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
        User fakeUser =
            new User(
             UUID.randomUUID(),
                "test_username",
                "$2a$10$bBiLUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
                Instant.now());
                
        Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

        Mockito.when(mockRequest.getParameter("AboutMe")).thenReturn("testBio");

        ProfileServlet1.doPost(mockRequest, mockResponse);

        fakeUser.setBio("testBio");
        

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(mockUserStore).updateUser(userCaptor.capture());
        Assert.assertEquals("testBio", userCaptor.getValue().getBio());


        Mockito.verify(mockResponse).sendRedirect("/profile");
     }


}