<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"	href="<c:url value="/resources/css/style.css" />">
<title>Transaction log</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>
		Select ATM for Transaction log :
		
		<c:out value="${UserAccount.msg}" />
	</h1>
	<div>
		<sf:form method="POST" commandName="UserAccount" action="/bbf4/emp/viewatmlog">
			<table align="center">				
				
				<tr>
					<td><sf:label path="atmname" cssErrorClass="error">Select Atm </sf:label>:
					<td><sf:select path="atmname">
							<sf:option value="ATM1">Atm 1</sf:option>
							<sf:option value="ATM2">Atm 2</sf:option>
							<sf:option value="ATM3">Atm 3</sf:option>
							
						</sf:select></td>
				</tr>
				
				<tr>
				<td colspan="2" align="center"><input type="submit"
						value="Request Transaction Log" /></td>
					
				</tr>

			</table>
		</sf:form>
	</div>
	
	<%@ include file="footer.jsp"%>
</body>

</html>