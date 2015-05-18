<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<title>Employee Banking</title>
</head>
<body>
	<sec:authorize access="hasRole('EMPL') or hasRole('ADMN')" var="isEmpl" />
	<sec:authorize access="hasRole('ADMN')" var="isAdmn" />
	<%@ include file="header.jsp"%>
	<h1>
		<c:out value="${userAccount.msg}" />
		<br>Hello
		<sec:authorize var="loggedIn" access="isAuthenticated()" />
		<c:choose>
			<c:when test="${loggedIn}">
				<%=request.getUserPrincipal().getName()%>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		<br> Bank Employee Page. Select Your Option
	</h1>
	<div>
		<table align="center">
			<tr>
				<td>
					<div class="boxhead">
						<div class="modern1">
							<font color="white" size="5px"> <a href="/bbf4/emp/lndmny"><font
									color="white">Lend Money to Other Bank</font></a></font>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="boxhead">
						<div class="modern1">
							<font color="white" size="5px"> <a
								href="/bbf4/emp/viewatmlog"><font color="white">View
										Atm Log</font></a></font>
						</div>
					</div>
				</td>

			</tr>
			<tr>
				<td>
					<div class="boxhead">
						<div class="boxhead">
							<div class="modern1">
								<font color="white" size="5px"> <a
									href="/bbf4/emp/viewbnkmoney"><font color="white">View
											Bank Money</font></a></font>
							</div>
						</div>
					</div>
				</td>

			</tr>

		</table>
	</div>
</body>
</html>