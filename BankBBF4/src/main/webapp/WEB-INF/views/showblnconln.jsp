<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="true"%>
<html>
<head>
<link rel="stylesheet" type="text/css"	href="<c:url value="/resources/css/style.css" />">
<title>Online Banking</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div align="center">
		<table align="center">
			<tr>
				<td><c:out value="${UserAccount.msg}" /></td>
			</tr>
			<tr>
				<td><font color="white" size="5px"> <a
						href="/bbf4/onln"><font color="white">
								Back to Online Banking</font></a></font></td>
			</tr>
		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>