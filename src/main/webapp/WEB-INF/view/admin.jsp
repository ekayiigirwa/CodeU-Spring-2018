<!DOCTYPE html>
<html>
<head>
  <title>Admin</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null) { %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">
    <h1>Admin</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
    
    <h2>Welcome to the Admin Page</h2>
    <p>Authorized admins:</p>
    <!-- dummy prototype data -->
    <ul>
        <li>Eduige</li>
        <li>Liana</li>
        <li>Stephen</li>
    </ul>

  <h1>Statistics</h1>
  <ul>
      <li>Users: <%= request.getAttribute("numberOfUsers") %></li>
      <li>Conversations: <%= request.getAttribute("numberOfConversations")%></li>
      <li>Messages: <%=request.getAttribute("numberOfMessages") %></li>
       <li>Newest User: <%= request.getAttribute("newestUser")  %></li>
  

  </ul>

 
   
  </div>
</body>
</html>