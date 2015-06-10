<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"	href="<c:url value="/resources/css/style.css" />">
<title>Bank Cash Details </title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>
		Cash Details for Bank 4 :
	</h1>
	<div>
		<table align="center">
			<tr>
				<td align="center">Id</td>
				<td align="center">Account Id</td>
				<td align="center">Bank Name</td>
				<td align="center">Amount</td>
				
			</tr>

			<c:forEach items="${CashDetailsList}" var="cdtls">
				<tr>
					<td><c:out value="${cdtls.id}" /></td>
					<td><c:out value="${cdtls.acntId}" /></td>
					<td><c:out value="${cdtls.bankNm}" /></td>
					<td><c:out value="${cdtls.amount}" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="4" align = "center"><font color="white" size="5px"> <a
						href="/bbf4/emp"><font color="white">
								Back to employee Page</font></a></font></td>
			</tr>

		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>

</html>