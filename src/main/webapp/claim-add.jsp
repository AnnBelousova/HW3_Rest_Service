<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add claim page</title>
</head>
<body>
<form method="post" action="ClaimAddServlet">
  <input type="hidden" name="user_id">
  <label>Claim description: </label>
  <input type="text" name="description" required>
  <br>
  <label>Claim date: </label><br>
  <input type="date" name="claim_date">
  <br>
  <input type="submit" name="submit" value="submit">
</form>
</body>
</html>
