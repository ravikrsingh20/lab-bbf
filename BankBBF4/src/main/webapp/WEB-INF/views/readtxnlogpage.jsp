<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>ATM Banking!! Please Select your option</h1>
	<div>
		<table align="center">
			<tr>
				<td><font color="white" size="5px"> <a href="cashwithdrawl"><font
							color="white">Withdraw Cash</font></a></font></td>
			</tr>
			<tr>
				<td><font color="white" size="5px"> <a href="readtxnlog"><font
							color="white">Read Transaction Log</font></a></font></td>

			</tr>

		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>