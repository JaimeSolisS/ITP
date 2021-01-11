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
    <li style="float:right"><a class="active" href="LogoutServlet">Logout</a></li>
</ul>
<h2 style="text-align: center">Login</h2>

<p style="color:red; text-align: center"> ${sessionScope.Message}</p>

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
</form>

    <div class="container" >
        <span>
             <label>Admin role</label>
            <br>
            <label>Username: admin</label>
            <br>
            <label>Password: pass</label>
        </span>
    </div>
    <div class = "container">
          <span>
             <label>User role</label>
            <br>
            <label>Username: user(1-9)</label>
            <br>
            <label>Password: pass(1-9)</label>
        </span>
    </div>

</body>
</html>