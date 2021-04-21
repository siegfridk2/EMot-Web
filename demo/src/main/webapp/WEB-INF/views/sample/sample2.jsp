<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>seq</th>
				<th>title</th>
				<th>body</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty LIST }">
					<c:forEach items="${LIST }" var="data">
						<tr>
							<td>
								<c:choose>
									<c:when test="${not empty data.seq }">
										${data.seq }
									</c:when>
									<c:otherwise>
										N/A
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${not empty data.title }">
										${data.title }
									</c:when>
									<c:otherwise>
										N/A
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${not empty data.body }">
										${data.body }
									</c:when>
									<c:otherwise>
										N/A
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<td colspan="3">list is empty</td>
				</c:otherwise>
			</c:choose>
		</tbody>

	</table>
	
	<a href="${pageContext.request.contextPath}/test3">3페이지로 이동</a>

</body>
</html>