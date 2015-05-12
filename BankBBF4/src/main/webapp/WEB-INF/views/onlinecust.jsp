<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<title>Customer online Banking</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<c:out value="${userAccount.msg}" />
	<h1>Customer Online Banking. Select your option</h1>
	<div>
		<table align="center">
			<tr>
				<td>
					<div class="boxhead">
						<div class="modern1">
							<font color="white" size="5px"> <a href="/bbf4/onln/plcwrtrns"><font
									color="white">Place Wire Transfer</font></a></font>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="boxhead">
						<div class="modern1">
							<font color="white" size="5px"> <a href="/bbf4/onln/viewtrns"><font
									color="white">Read Transaction</font></a></font>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="boxhead">
						<div class="modern1">
							<font color="white" size="5px"> <a href="/bbf4/onln/viewbal"><font
									color="white">View Balance</font></a></font>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>