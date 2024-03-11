<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
<form method="post" action="UserServlet">
  <label>User name: </label>
  <input type="text" name="username" required>
  <br>
  <label>Email: </label>
  <input type="text" name="email" required>
  <br>
  <input type="submit" name="submit" value="submit">
</form>
    <div>
      <% String table = (String) request.getAttribute("table");%>
          <%= table %>
    </div>
</body>
</html>
