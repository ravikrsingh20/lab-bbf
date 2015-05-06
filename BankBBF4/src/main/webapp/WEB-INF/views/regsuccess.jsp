<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style.css" />">
<title>Registration Status</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>
		Registration Done!! Please save your details 
		<c:out value="${userAccount.fname}" />
		<c:out value="${userAccount.lname}" />
	</h1>
	<div>
		<table align="center">
			<tr>
				<td>First Name:</td>
				<td><c:out value="${userAccount.fname}" /></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><c:out value="${userAccount.lname}" /></td>
			</tr>
			<tr>
				<td>IBAN No.:</td>
				<td><c:out value="${userAccount.acntid}" /></td>
			</tr>
			<tr>
				<td>ATM Pin:</td>
				<td><c:out value="${userAccount.atmpin}" /></td>
			</tr>
			<tr>
				<td>Online Pin :</td>
				<td><c:out value="${userAccount.onlnpin}" /></td>
			</tr>
			<tr>
				<td>Balance:</td>
				<td><c:out value="${userAccount.balance}" /></td>
			</tr>

		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>