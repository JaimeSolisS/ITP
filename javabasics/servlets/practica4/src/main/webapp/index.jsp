<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="style.css">

<head>
    <title>JSP - Hello World</title>
</head>
<body>
<ul>
    <li><a href="index.jsp">Login</a></li>
    <li><a href="Register.jsp">Register</a></li>
    <li style="float:right"><a class="active" href="LogoutServlet">Logout</a></li>
</ul>
<h2 style="text-align: center">Login</h2>

<form action="LoginServlet" method="post">
    <div class="container">
        <div>
            <label><b>Username</b></label>
            <input type="text" placeholder="Enter Username" name="username" required>

            <label><b>Password</b></label>
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