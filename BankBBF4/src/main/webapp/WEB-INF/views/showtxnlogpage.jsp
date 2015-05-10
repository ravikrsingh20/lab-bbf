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
		<c:out value="${userAccount.acntid}" />
	</h1>
	<div>
		<table align="center">
			<tr>
				<td>Execution Date</td>
				<td>Amount</td>
				<td>Message</td>
			</tr>

			<c:forEach items="${TxnDtlsList}" var="txnDtls">
				<tr>
					<td><c:out value="${txnDtls.execdt}" /></td>
					<td><c:out value="${txnDtls.txnamt}" /></td>
					<td><c:out value="${txnDtls.msg}" /></td>
				</tr>
			</c:forEach>


		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>

</html>