<!DOCTYPE html>
<html>
<head>
  <title>Admin</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">Bagheera Band 33</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null) { %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activityfeed">Activity Feed</a>
    <% if((request.getSession().getAttribute("user") != null)){ %>
    	<a href="/logout">Logout</a> <% } %>
  </nav>

  <div id="container">

    <h2>Welcome to the Administration Page</h2>
  

  <h1>Statistics</h1>
  <ul>
      <li>Users: <%= request.getAttribute("numberOfUsers") %></li>
      <li>Conversations: <%= request.getAttribute("numberOfConversations")%></li>
      <li>Messages: <%=request.getAttribute("numberOfMessages") %></li>
       <li>Newest User: <%= request.getAttribute("newestUser")  %></li>
      <li>Most Active User: <%= request.getAttribute("mostActiveUser")%></li>
      <li>Wordiest User: <%=request.getAttribute("wordiestUser")%> </li>

  </ul>
 
   
  </div>
</body>
</html>