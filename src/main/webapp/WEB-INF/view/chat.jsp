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
<%-- <meta charset="utf-8" > 
--%>
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
     function mention(){
      var x = document.getElementById("currentUsers").value;
      y = document.getElementById("chatSpace").value + "@" + x;
      document.getElementById("chatSpace").value = y;
      console.log(x);
    }

  // scroll the chat div to the bottom
  function scrollChat() {
    var chatDiv = document.getElementById('chat');
    chatDiv.scrollTop = chatDiv.scrollHeight;
  };
</script>
</head>
<body onload="scrollChat()">

<nav>
  <a id="navTitle" href="/">CodeU Chat App</a>
  <a href="/conversations">Conversations</a>
    <% if (request.getSession().getAttribute("user") != null) { %>
  <a href = "/profile" > Hello <%= request.getSession().getAttribute("user") %> ! </a>
  <% } else { %>
    <a href="/login">Login</a>
  <% } %>
  <a href="/about.jsp">About</a>
  <a href="/activityfeed">Activity Feed</a>
  <% if((request.getSession().getAttribute("user") != null)){ %>
    	<a href="/logout">Logout</a> <% } %>
</nav>

<div id="container">

  <h1><%= conversation.getTitle() %>
    <a href="" style="float: right">&#8635;</a></h1>

  <hr/>

  <div id="chat">
    <ul>
  <%
    for (Message message : messages) {
      String author = UserStore.getInstance()
        .getUser(message.getAuthorId()).getName();
  %>
    <li><strong><%= author %>:</strong> <%= message.getContent() %></li>
  <%
    }
  %>
    </ul>
  </div>

  <hr/>

  <% if (request.getSession().getAttribute("user") != null) { %>
  <form action="/chat/<%= conversation.getTitle() %>" method="post">

      <input id = "chatSpace" type="text" name="message"> <button onclick = "this.innerHTML='test'" type="mention">mention</button>
      
      <% List<User> users =(List<User>) request.getAttribute("usersInConversation"); %>
        <select id = "currentUsers" onchange = "mention()">
        <%System.out.println(users);%> <!-- For testing -->

        <% for (User user: users){ %>
        <%if(user.getName() == null){
          continue;
        }%>
        <option value="<%= user.getName() %>"><%= user.getName()%></option>
        }
        <option value = "test">test</option>

        <%}%>
        </select>
        <br/>
        <input type = "submit" name="emoticon" value= &#x1F601;>
        <input type = "submit" name="emoticon" value= &#x1F60D;>
        <input type = "submit" name="emoticon" value= &#x1F44D;>
        <input type = "submit" name="emoticon" value= &#x1F44E;>
        <input type = "submit" name="emoticon" value= &#x1F914;>
        <br/><br/>
  </form>
  <% } else { %>
    <p><a href="/login">Login</a> to send a message.</p>
  <% } %>

  <hr/>

</div>

</body>
</html>
<!-- 
 PSEUDO CODE

 to get drop down list of users, ensure that you have a list of users fomr the conversation being worked on at the time.

 Once you have a list of those users, use a loop and the select tag to create a list of all users in the conversation

 for prototype purposes, create a dropdown with all the users in the app.

 -->

 <!--
 PSEUDO CODE FOR NAME INSERT

Upon clicking a user name, grab the element text and insert it into the text bar


 -->
