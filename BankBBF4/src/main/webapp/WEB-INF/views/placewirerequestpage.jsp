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
	<h1>Online Banking!! Please fill below details to place wire transfer</h1>
	<c:out value="${TxnDtls.msg}" />
	<div>
		<sf:form method="POST" commandName="TxnDtls" action="/bbf4/onln/plcwrtrns">
			<table align="center">				
				<tr>
					<td><sf:label path="txncrdracntid" cssErrorClass="error">Transfer to IBAN No. </sf:label>:
					</td>
					<td><sf:input path="txncrdracntid" cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><sf:label path="txncrdrbnknm" cssErrorClass="error">Transfer to Bank </sf:label>:
					<td><sf:select path="txncrdrbnknm">
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
					<td><sf:label path="txnamt" cssErrorClass="error">Enter amount to transfer</sf:label>:
					</td>
					<td><sf:input path="txnamt" cssErrorClass="error" /></td>
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