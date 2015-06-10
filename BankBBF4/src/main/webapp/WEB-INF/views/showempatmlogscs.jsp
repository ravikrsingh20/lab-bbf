<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"	href="<c:url value="/resources/css/style.css" />">
<title>ATM Transaction log</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>
		Transaction log for ATM :
		<c:out value="${UserAccount.atmname}" />
		<c:out value="${UserAccount.msg}" />
	</h1>
	<div>
		<table align="center">
			<tr>
				<td align="center">Transaction Id</td>
				<td align="center">Execution Date</td>
				<td align="center">Amount</td>
				<td align="center">Message</td>
			</tr>

			<c:forEach items="${TxnDtlsList}" var="txnDtls">
				<tr>
					<td><c:out value="${txnDtls.txnid}" /></td>
					<td><c:out value="${txnDtls.execdt}" /></td>
					<td><c:out value="${txnDtls.txnamt}" /></td>
					<td><c:out value="${txnDtls.msg}" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="4" align = "center"><font color="white" size="5px"> <a
						href="/bbf4/emp"><font color="white">
								Back to Employee Banking</font></a></font></td>
			</tr>

		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>

</html>