<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<title>Transaction Log</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>Please enter below details to read transactions log</h1>
	<c:out value="${UserAccount.msg}" />
	<div>
		<sf:form method="POST" commandName="UserAccount"
			action="/bbf4/atmbank/readtxnlog">
			<table align="center">
				<tr>
					<td><sf:label path="bnkname" cssErrorClass="error">Select Your Bank </sf:label>:
						<td><sf:select path="bnkname">
							<sf:option value="BANK4">Bank4</sf:option>
							<sf:option value="BANK1">Bank1</sf:option>
							<sf:option value="BANK2">Bank2</sf:option>
							<sf:option value="BANK3">Bank3</sf:option>
							<sf:option value="BANK5">Bank5</sf:option>
							<sf:option value="BANK6">Bank6</sf:option>
							<sf:option value="BANK7">Bank7</sf:option>
							<sf:option value="BANK8">Bank8</sf:option>
							<sf:option value="BANK9">Bank9</sf:option>
						</sf:select></td>
				</tr>
				<tr>
					<td><sf:label path="atmname" cssErrorClass="error">Select Your ATM </sf:label>:
					
					<td><sf:select path="atmname">
							<sf:option value="ATM1">ATM1</sf:option>
							<sf:option value="ATM2">ATM2</sf:option>
							<sf:option value="ATM3">ATM3</sf:option>
						</sf:select></td>
				</tr>
				<tr>
					<td><sf:label path="acntid" cssErrorClass="error">Enter your IBAN No. </sf:label>:
					</td>
					<td><sf:input path="acntid" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><sf:label path="atmpin" cssErrorClass="error">Enter your ATM pin</sf:label>:
					</td>
					<td><sf:password path="atmpin" cssErrorClass="error" autocomplete="off" /></td>
				</tr>				
				<tr>
				<td colspan="2" align="center"><input type="submit"
						value="Show Transactions" /></td>
				</tr>

			</table>
		</sf:form>
	</div>
	<%@ include file="footer.jsp"%>
	</	body>
</html>