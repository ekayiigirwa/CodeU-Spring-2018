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
<!DOCTYPE html>
<html>
<head>
  <title>Bagheera-Band-33</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">Bagheera Band 33</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/profile"> Hello <%= request.getSession().getAttribute("user") %>!</a>
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
      <h1> About the Developers:</h1>
      <h2>THE BAGHEERA-BAND-33 TEAM </h2>
        <img src= "images/bagheeraSaves.gif" height = "250" width = "500"  alt = "Bagheera saving." >
      <p>
        This chat app is the product of team Bagheera-Band-33. 
        <ul> 
          <li> <strong> Eduige Kayigirwa: </strong> Will work on profile pages.</li>
          <li> <strong> Stephen Seymour: </strong> Will develop the the Admin Page. </li>
          <li> <strong> Liana Najaroen: </strong> Will work on the activity feed. </li>
        </ul>  
      </p>

    </div>
  </div>
</body>
</html>
