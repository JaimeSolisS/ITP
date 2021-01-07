<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="style.css">
<style>
    ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #333;
    }

    li {
        float: left;
    }

    li a {
        display: block;
        color: white;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
    }

    li a:hover:not(.active) {
        background-color: #111;
    }

    .active {
        background-color: #4CAF50;
    }
</style>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<ul>
    <li><a href="index.jsp">Login</a></li>
    <li><a href="LoginServlet">Register</a></li>
    <li style="float:right"><a class="active" href="LogoutServlet">Logout</a></li>
</ul>
<h2 style="text-align: center">Login</h2>

<form action="LoginServlet" method="post">
    <div class="container">
        <div>
        <label ><b>Username</b></label>
        <input type="text" placeholder="Enter Username" name="username" required>

        <label ><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>

        <button type="submit">Login</button>
        </div>
    </div>

    <div class="container" >
        <span>
            <label>Username: admin</label>
            <br>
            <label>Password: qwerty</label>
        </span>


    </div>
</form>
</body>
</html>