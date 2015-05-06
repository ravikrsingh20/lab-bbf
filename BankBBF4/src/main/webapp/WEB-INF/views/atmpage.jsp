<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<title>ATM Banking</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>ATM Banking!! Please Select your option</h1>
	<div>
		<table align="center">
			<tr>
				<td><font color="white" size="5px"> Select Your Bank  </font></td>
				<td><select name="bankName">
					 <option value="Bank4">Bank4</option>
 					 <option value="Bank1">Bank1</option>
					 <option value="Bank2">Bank2</option>
					 <option value="Bank3">Bank3</option>					 
					 <option value="Bank5">Bank5</option>
					 <option value="Bank6">Bank6</option>
					 <option value="Bank7">Bank7</option>
					 <option value="Bank8">Bank8</option>
					 <option value="Bank9">Bank9</option>					 
					</select>
				</td>
			</tr>
			<tr>
				<td><font color="white" size="5px"> Select Your ATM  </font></td>
				<td><select name="atmName">
 					 <option value="Atm1">ATM1</option>
					 <option value="Atm2">ATM2</option>
					 <option value="Atm3">ATM3</option>
					 </select>
				</td>
			</tr>
			<tr>
				<td><font color="white" size="5px"> <a
						href="/atmbank/cashwithdrawl"><font color="white">Withdraw Cash</font></a></font></td>
				<td><font color="white" size="5px"> <a href="/atmbank/readtxnlog"><font
							color="white">Read Transaction Log</font></a></font></td>
			</tr>

		</table>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>