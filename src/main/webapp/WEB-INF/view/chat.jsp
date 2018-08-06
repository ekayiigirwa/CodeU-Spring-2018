<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<!--<%@ page import="codeu.model.data.User" %>-->
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");

UserStore userStores = (UserStore) request.getAttribute("userStores");
%>

<!DOCTYPE html>
<html>
<head>

  <title><%= conversation.getTitle() %></title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">

  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
   
    /*
     * Adds text fromt the selected item on the drop-down list to the input field
     */

     var isMentioned = false;
    function mention(){
      var x = document.getElementById("currentUsers").value;
      var y = document.getElementById("chatSpace").value + "@" + x;
      document.getElementById("chatSpace").value = y;
      console.log(x);

      isMentioned = true;
    }

     // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    }
  </script>
</head>
<body onload="scrollChat()">

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
    <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">

    <h1><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
      <ul>
    <%
      String currentUserName= "@" + request.getSession().getAttribute("user");

      for (Message message : messages) {
        String author = UserStore.getInstance().getUser(message.getAuthorId()).getName();
    %>
      <li><strong><%= author %>:</strong> 

     <%  if(message.getContent().contains(currentUserName)){ %>
       
          <mark><strong><%= message.getContent() %><strong></mark>
       <%} else{ %>
        <span style = "font-weight:normal"><%= message.getContent() %></span>
      <%  
       }
      %>

      </li>
    <%
      }
    %>
      </ul>
    </div>

    <hr/>

    <% if (request.getSession().getAttribute("user") != null) { %>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input id = "chatSpace" type="text" name="message"> 

      
<%
    List<User> users =
      (List<User>) request.getAttribute("usersInConversation");
      %>
        <select id = "currentUsers" onchange = "mention()">
        <%System.out.println(users);%> <!-- For testing -->
        
        <option value = "" disabled selected hidden>Conversation Members</option>


        <% for (User user: users){ %>
        <%if(user.getName() == null){
          continue;
        }%>
         <option value="<%= user.getName() %>"><%= user.getName()%></option>
        }
  
        <%}%>

        </select>
        <br/>
        <button type="submit">Send</button>
    </form>
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>

    <hr/>

  </div>
    <li>
      <ul>
        

      </ul>
    </li>

    <!--- testing purposes only -->
    <div>
        <%
        for (Message message : messages) {
        
        System.out.println(message);
        }
    %>
        
    </div>
</body>
</html>