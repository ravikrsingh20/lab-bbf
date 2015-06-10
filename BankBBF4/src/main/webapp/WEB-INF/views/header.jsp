<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<table>
		<tr>
			<td>
				<div class="boxhead">
					<div class="modern1">
						<font color="white" size="5px"> <a href="/bbf4/register"><font
								color="white">Register Account</font></a></font>
					</div>
				</div>
			</td>
			<td><sec:authorize var="loggedIn" access="isAuthenticated()" />
				<c:choose>
					<c:when test="${loggedIn}">
						<div class="boxhead">
							<div class="modern1">
								<font color="white" size="5px"> <a
									href="<c:url value='/logout'/>"><font color="white">Log
											Out</font></a></font>
					</c:when>
					<c:otherwise>
						<div class="boxhead">
							<div class="modern1">
								<font color="white" size="5px"> <a href="/bbf4/login"><font
										color="white">Online Login</font></a></font>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				</div></td>
			<td>
				<div class="boxhead">
					<div class="modern1">
						<font color="white" size="5px"> <a href="/bbf4/atmbank"><font
								color="white">ATM Banking</font></a></font>
					</div>
				</div>
			</td>
			<td width="50%"></td>
		</tr>
	</table>
	<div class="welcome" style="background: black">
		<table>
			<tr>
				<h1>
					<font color="white" size="24px"> Welcome to Bank BBF4. </font>
				</h1>
				<h2 style="color: thistle; font-family: Comic Sans MS;">Perform
					Online Banking, ATM Banking, and Bank to Bank transfer.</h2>

				</br>
			</tr>
		</table>
	</div>
</div>