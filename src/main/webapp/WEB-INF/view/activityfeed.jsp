<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Variables" %>
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
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activityfeed">Activity Feed</a>
  </nav>

  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
    <h1>Activity Feed</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
 	<p>	Here's everything that's happened on the site so far!</p>
      
    <div id="activityfeed">  
      <ul>
    <%
 	ArrayList<Variables> events = new ArrayList<Variables>();
 	events.addAll(PersistentStorageAgent.getInstance().loadUsers());
 	events.addAll(PersistentStorageAgent.getInstance().loadConversations());
 	events.addAll(PersistentStorageAgent.getInstance().loadMessages());
 	
 	Collections.sort(events, new Comparator<Variables>() {
    @Override
    public int compare(Variables o1, Variables o2) {
        return o1.getCreationTime().compareTo(o2.getCreationTime());
    }
	});
	
	Collections.reverse(events); 
 	
 	for(Variables event : events){
      		if(event instanceof User){
      		User user = (User) event;
		      	%>
		      	<li><strong><%= Date.from(user.getCreationTime()) %>:</strong> <%= user.getName() %> joined! </li>
		      	<%
      		}
      		
      		if (event instanceof Message){
      			Message message = (Message) event;
      			String author = UserStore.getInstance().getUser(message.getAuthorId()).getName();
      			String convoName = ConversationStore.getInstance().getConversationWithID(message.getConversationId()).getTitle();
		        %>
		      	<li><strong><%= Date.from(message.getCreationTime()) %>:</strong> <%= author %> sent a message in <a href="/chat/<%= convoName  %>">
		        <%= convoName %></a>: <%= message.getContent() %></li>
      			<%
      		}
      		
      		if (event instanceof Conversation){
      		  Conversation conversation = (Conversation) event;
      		  Date convoDate = Date.from(conversation.getCreationTime());
		      String name = UserStore.getInstance().getUser(conversation.getOwnerId()).getName();
		      %>
		      <li><strong><%= convoDate %>:</strong> <%= name %> created a new conversation: <a href="/chat/<%= conversation.getTitle() %>">
		      <%= conversation.getTitle() %></a></li>
		      <%
      		}
      	}      
      %>    
      </ul>
 
  </div>
</body>
</html>

