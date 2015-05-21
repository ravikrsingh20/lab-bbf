<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"	href="<c:url value="/resources/css/style.css" />">
<title>Transaction log</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>
		Transaction log for Account :
		<c:out value="${UserAccount.acntid}" /><br>
	</h1>
	<div>
		<table align="center">
			<tr>
				<td align="center">Execution Date</td>
				<td align="center">Amount</td>
				<td align="center">Message</td>
			</tr>

			<c:forEach items="${JsonTxnDtlsList}" var="txnDtls">
				<tr>
					<td><c:out value="${txnDtls.execDate}" /></td>
					<td><c:out value="${txnDtls.amount}" /></td>
					<td><c:out value="${txnDtls.message}" /></td>
				</tr>
			</c:forEach>
	<tr>
				<td colspan="3" align = "center"><font color="white" size="5px"> <a
						href="/bbf4/atmbank"><font color="white">
								Back to Atm Page</font></a></font></td>
			</tr>

		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>

</html>