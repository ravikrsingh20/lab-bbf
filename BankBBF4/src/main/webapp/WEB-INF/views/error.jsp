<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<title>Access Denied</title>
</head>
<body>
<%@ include file="header.jsp"%>
<h1>Something went wrong</h1>
<h1>${reason}</h1>
<h1>${exception}</h1>
 <%@ include file="footer.jsp"%>
</body>
</html>