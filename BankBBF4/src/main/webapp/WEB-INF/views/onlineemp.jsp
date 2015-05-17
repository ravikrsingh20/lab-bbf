<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<title>Employee Banking</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<c:out value="${userAccount.msg}" />
	<h1>Online Employee Portal. Select Your option</h1>
	<div>
		<table align="center">			
			<tr>
				<td>
					<div class="boxhead">
						<div class="modern1">
							<font color="white" size="5px"> <a href="/bbf4/emp/lndmny"><font
									color="white">Lend money to other bank</font></a></font>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="boxhead">
						<div class="modern1">
							<font color="white" size="5px"> <a href="/bbf4/atmbank/viewatmlog"><font
									color="white">View ATM Log</font></a></font>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="boxhead">
						<div class="modern1">
							<font color="white" size="5px"> <a href="/bbf4/atmbank/lndmny"><font
									color="white">View Bank Cash Details</font></a></font>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>