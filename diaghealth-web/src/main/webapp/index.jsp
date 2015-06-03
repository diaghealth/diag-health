<html>
<body>
<h2>Hello World!</h2>
<%
   // New location to be redirected
   String site = new String("/diagonline-web/login");
   response.setStatus(response.SC_MOVED_TEMPORARILY);
   response.setHeader("Location", site); 
%>
</body>
</html>
