<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Login" %>
<%@ page import="codeu.model.data.Logout" %>
<%@ page import="codeu.model.data.Activity" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="codeu.model.store.persistence.PersistentStorageAgent" %>


<!DOCTYPE html>
<html>
<head>
  <title>Activity Feed</title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  
  <style>
    #activityfeed {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var activityfeedDiv = document.getElementById('chat');
      activityfeedDiv.scrollTop = activityfeedDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollChat()">

  <nav>
    <a id="navTitle" href="/">Bagheera Band 33</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activityfeed">Activity Feed</a>
    <% if((request.getSession().getAttribute("user") != null)){ %>
    	<a href="/logout">Logout</a> <% } %>
  </nav>

  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
    <h1>Activity Feed</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
    
    <% if((request.getSession().getAttribute("user") != null)){ %>
    
 	<p>	Here's everything that's happened on the site so far!</p>
      
    <div id="activityfeed">  
      <ul>
    <%
 	ArrayList<Activity> events = new ArrayList<Activity>();
 	PersistentStorageAgent Instant = PersistentStorageAgent.getInstance();
 	events.addAll(Instant.loadUsers());
 	events.addAll(Instant.loadConversations());
 	events.addAll(Instant.loadMessages());
 
 	ArrayList<Activity> e = new ArrayList<Activity>();
 	e.addAll(events);
 
	for(Activity event : e){
 		if(event instanceof User){
 			User user = (User) event;
 			if(!user.getLoginArr().isEmpty())
 				events.addAll(user.getLoginArr());
 			if(!user.getLogoutArr().isEmpty())
 				events.addAll(user.getLogoutArr());
 			}
 	}
 	 
 	Collections.sort(events, new Comparator<Activity>() {
    @Override
    public int compare(Activity o1, Activity o2) {
        return o1.getCreationTime().compareTo(o2.getCreationTime());
    }
	});
	
	Collections.reverse(events); 
 	
 	for(Activity event : events){
      		if(event instanceof User){
      			User user = (User) event;
		      	%>
		      	<li><strong><%= Date.from(user.getCreationTime()) %>:</strong> <%= user.getName() %> joined! </li>
		      	<%
		      	}
		      	
  			else if (event instanceof Login){
      		Login login = (Login) event;
      			%>
		      	<li><strong><%= Date.from(login.getTime()) %>:</strong> <%= login.getName() %> logged in! </li>
		      	<%
      		}
      		
      		else if (event instanceof Logout){
      		Logout logout = (Logout) event;
      			%>
		      	<li><strong><%= Date.from(logout.getTime()) %>:</strong> <%= logout.getName() %> logged out! </li>
		      	<%
      		}
      		
      		else if (event instanceof Message){
      			Message message = (Message) event;
      			String author = UserStore.getInstance().getUser(message.getAuthorId()).getName();
      			String convoName = ConversationStore.getInstance().getConversationWithID(message.getConversationId()).getTitle();
		        %>
		      	<li><strong><%= Date.from(message.getCreationTime()) %>:</strong> <%= author %> sent a message in <a href="/chat/<%= convoName  %>">
		        <%= convoName %></a>: <%= message.getContent() %></li>
      			<%
      		}
      		
      		else if (event instanceof Conversation){
      		  Conversation conversation = (Conversation) event;
      		  Date convoDate = Date.from(conversation.getCreationTime());
		      String name = UserStore.getInstance().getUser(conversation.getOwnerId()).getName();
		      %>
		      <li><strong><%= convoDate %>:</strong> <%= name %> created a new conversation: <a href="/chat/<%= conversation.getTitle() %>">
		      <%= conversation.getTitle() %></a></li>
		      <%
      		}
      	}
       %> </ul> <%
     }
     
     else { %>
      <p><a href="/login">Login to see everything that's happened on the site so far!</a></p>
    <% } %>     
 
  </div>
</body>
</html>

