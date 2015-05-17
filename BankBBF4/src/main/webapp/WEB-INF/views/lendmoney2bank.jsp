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
	<h1>Show ATM Log </h1>
	<c:out value="${TxnDtls.msg}" />
	<div>
		<sf:form method="POST" commandName="UserAccount" action="/bbf4/atmbank/viewatmlog">
			<table align="center">				
				
				<tr>
					<td><sf:label path="bnkname" cssErrorClass="error">Select ATM</sf:label>:
					<td><sf:select path="bnkname">
							<sf:option value="ATM1">Atm1</sf:option>
							<sf:option value="ATM2">Atm2</sf:option>
							<sf:option value="ATM3">Atm3</sf:option>
						</sf:select></td>
				</tr>
				
				<tr>
				<td colspan="2" align="center"><input type="submit"
						value="Show Atm Log" /></td>
					
				</tr>

			</table>
		</sf:form>
	</div>
	<%@ include file="footer.jsp"%>
	
</body>
</html>