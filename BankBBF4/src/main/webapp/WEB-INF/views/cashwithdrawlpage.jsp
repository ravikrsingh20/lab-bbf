<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<link rel="stylesheet" type="text/css"	href="<c:url value="/resources/css/style.css" />">
<title>ATM Banking</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>ATM Banking!! Please fill below details to withdraw cash</h1>
	<div>
		<sf:form method="POST" commandName="UserAccount">
			<sf:errors path="*" element="div" cssClass="errors" />
			<table align="center">
				<tr>
					<td><sf:label path="acntid" cssErrorClass="error">Enter your IBAN No.</sf:label>:
					</td>
					<td><sf:input path="acntid" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><sf:label path="atmpin" cssErrorClass="error">Enter your 4 digit pin</sf:label>:
					</td>
					<td><sf:input path="atmpin" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><sf:label path="balance" cssErrorClass="error">Enter amount to withdraw</sf:label>:
					</td>
					<td><sf:input path="balance" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Withdraw Cash" /></td>
				</tr>
			</table>
		</sf:form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>