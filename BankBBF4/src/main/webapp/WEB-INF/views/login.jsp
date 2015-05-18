<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="true"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style.css" />">
<title>Online Login</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>Enter your details to log in</h1>
	<div>
		<c:url value="/login" var="loginUrl" />
		<form action="${loginUrl}" method="post">
			<c:if test="${param.error != null}">
				<p>Invalid username and password.</p>
			</c:if>
			<c:if test="${param.time > 1}">
				<p>Maximum 1 session allowed.</p>
			</c:if>
			<c:if test="${param.logout != null}">
				<p>You have been logged out.</p>
			</c:if>
			
			<p>
				<label for="username">IBAN No</label> <input type="text"
					id="username" name="username" />
			</p>
			<p>
				<label for="password">Password</label> <input type="password"
					id="password" name="password" />
			</p>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<button type="submit" class="btn">Log in</button>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>