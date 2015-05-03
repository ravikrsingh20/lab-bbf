<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>
<html>
<head>
<title>Bank BBF4</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style.css" />">
<title>Account Registration</title>
</head>
<body>
	<div id="header">
		<a href="/"> <img
			src="<c:url value="/resources/images/BankBBF4.jpg" />" border="0" /></a>
	</div>

	<h1>Register</h1>
	<div>
		<sf:form method="POST" commandName="UserAccount">
			<sf:errors path="*" element="div" cssClass="errors" />
			<sf:label path="fname" cssErrorClass="error">First Name</sf:label>: 
        <sf:input path="fname" cssErrorClass="error" />
			<br />
			<sf:label path="lname" cssErrorClass="error">Last Name</sf:label>: 
        <sf:input path="lname" cssErrorClass="error" />
			<br />
			<sf:label path="email" cssErrorClass="error">Email</sf:label>: 
        <sf:input path="email" cssErrorClass="error" />
			<br />
			<sf:label path="phoneno" cssErrorClass="error">Phone No.</sf:label>: 
        <sf:input path="phoneno" cssErrorClass="error" />
			<br />
			<sf:label path="Address" cssErrorClass="error">Address</sf:label>: 
        <sf:input path="Address" cssErrorClass="error" />
			<br />
			<input type="submit" value="Register" />
		</sf:form>
	</div>
	<div id="copy">Copyright &copy; Bank BBF4</div>
</body>
</html>