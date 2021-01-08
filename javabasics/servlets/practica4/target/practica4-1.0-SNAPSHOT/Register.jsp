<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" type="text/css" href="styleRegister.css">
<link rel="stylesheet" type="text/css" href="style.css">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>

<body>
<ul>
    <li><a href="index.jsp">Login</a></li>
    <li><a href="Register.jsp">Register</a></li>
    <li style="float:right"><a class="active" href="LogoutServlet">Logout</a></li>
</ul>
<div>welcome, ${sessionScope.username}</div>
<h2>Get Method</h2>
<div class="container">
    <form action="UserRegister" method="get">
        <div class="row">
            <div class="col-25">
                <label>First Name</label>
            </div>
            <div class="col-75">
                <input type="text" name="firstname" placeholder="Your name..">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label>Last Name</label>
            </div>
            <div class="col-75">
                <input type="text" name="lastname" placeholder="Your last name..">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label>Age</label>
            </div>
            <div class="col-75">
                <input type="number" name="age" min="0"  step="1" value="15">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label>Country</label>
            </div>
            <div class="col-75">
                <select name="country">
                    <option value="australia">Australia</option>
                    <option value="canada">Canada</option>
                    <option value="usa">USA</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label>Subject</label>
            </div>
            <div class="col-75">
                <textarea name="subject" placeholder="Write something.." style="height:200px">Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae elit non metus cursus ultrices nec id mauris. Aliquam finibus orci sed arcu cursus dapibus. Suspendisse pretium, diam vitae luctus venenatis, nibh nibh volutpat felis, vestibulum volutpat lacus massa quis odio. Pellentesque id gravida mi. Sed in facilisis enim. Duis non venenatis tortor. Morbi ultrices leo vel velit tempor, at aliquam libero consequat. Maecenas at tristique felis. Aliquam vel dapibus orci. Fusce imperdiet tempus est sit amet accumsan. Donec sagittis gravida magna, sit amet sagittis arcu vestibulum at. Donec imperdiet tellus non enim lacinia convallis. Mauris efficitur, metus vitae maximus commodo, leo augue hendrerit sem, quis pretium enim quam sit amet dolor. Aenean dapibus finibus ligula non congue. Nam volutpat enim vel nulla rhoncus pellentesque.</textarea>
            </div>
        </div>
        <div class="row">
            <input type="submit" value="Submit">
        </div>
    </form>
</div>

<h2>Post Method</h2>
<div class="container">
    <form action="UserRegister" method="post">
        <div class="row">
            <div class="col-25">
                <label>First Name</label>
            </div>
            <div class="col-75">
                <input type="text" name="firstname" placeholder="Your name..">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label>Last Name</label>
            </div>
            <div class="col-75">
                <input type="text" name="lastname" placeholder="Your last name..">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label>Age</label>
            </div>
            <div class="col-75">
                <input type="number" name="age" min="0"  step="1" value="15">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label>Country</label>
            </div>
            <div class="col-75">
                <select name="country">
                    <option value="australia">Australia</option>
                    <option value="canada">Canada</option>
                    <option value="usa">USA</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label>Subject</label>
            </div>
            <div class="col-75">
                <textarea name="subject" placeholder="Write something.." style="height:200px">Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae elit non metus cursus ultrices nec id mauris. Aliquam finibus orci sed arcu cursus dapibus. Suspendisse pretium, diam vitae luctus venenatis, nibh nibh volutpat felis, vestibulum volutpat lacus massa quis odio. Pellentesque id gravida mi. Sed in facilisis enim. Duis non venenatis tortor. Morbi ultrices leo vel velit tempor, at aliquam libero consequat. Maecenas at tristique felis. Aliquam vel dapibus orci. Fusce imperdiet tempus est sit amet accumsan. Donec sagittis gravida magna, sit amet sagittis arcu vestibulum at. Donec imperdiet tellus non enim lacinia convallis. Mauris efficitur, metus vitae maximus commodo, leo augue hendrerit sem, quis pretium enim quam sit amet dolor. Aenean dapibus finibus ligula non congue. Nam volutpat enim vel nulla rhoncus pellentesque.</textarea>
            </div>
        </div>
        <div class="row">
            <input type="submit" value="Submit">
        </div>
    </form>
</div>

</body>
</html>