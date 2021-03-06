<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="true"%>
<html>
<head>
<link rel="stylesheet" type="text/css"	href="<c:url value="/resources/css/style.css" />">
<title>ATM Banking</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>Lend Money to Other Bank</h1>
	<c:out value="${TxnDtls.msg}" />
	<div>
		<sf:form method="POST" commandName="UserAccount" action="/bbf4/atmbank/lndmny">
			<table align="center">				
				
				<tr>
					<td><sf:label path="bnkname" cssErrorClass="error">Lend to Bank </sf:label>:
					<td><sf:select path="bnkname">
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
					<td><sf:label path="amt" cssErrorClass="error">Enter amount to transfer</sf:label>:
					</td>
					<td><sf:input path="amt" cssErrorClass="error" /></td>
				</tr>
				<tr>
				<td colspan="2" align="center"><input type="submit"
						value="Place transfer request" /></td>
					
				</tr>

			</table>
		</sf:form>
	</div>
	<%@ include file="footer.jsp"%>
	
</body>
</html>