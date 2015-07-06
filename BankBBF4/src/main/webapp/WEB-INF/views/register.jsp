<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style.css" />">
<title>Account Registration</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>Register Your Account</h1>
	<c:out value="${userAccount.msg}" />
	<div>
		<sf:form method="POST" commandName="UserAccount">
			<sf:errors path="*" element="div" cssClass="errors" />
			<table align="center">
				<tr>
					<td><sf:label path="fname" cssErrorClass="error">First Name</sf:label>:
					</td>
					<td><sf:input path="fname" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><sf:label path="lname" cssErrorClass="error">Last Name</sf:label>:
					</td>
					<td><sf:input path="lname" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><sf:label path="email" cssErrorClass="error">Email</sf:label>:
					</td>
					<td><sf:input path="email" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><sf:label path="phoneno" cssErrorClass="error">Phone No.</sf:label>:
					</td>
					<td><sf:input path="phoneno" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><sf:label path="address" cssErrorClass="error">Address</sf:label>:</td>
					<td><sf:input path="address" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><sf:label path="balance" cssErrorClass="error">Initial Balance</sf:label>:</td>
					<td><sf:input path="balance" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Register" /></td>
				</tr>
			</table>
		</sf:form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>