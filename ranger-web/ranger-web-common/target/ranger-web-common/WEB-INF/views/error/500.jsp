<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html> 
	<head>
		<meta name="decorator" content="default" />
	</head>
	<body>
	
		系统内部错误
		
			<c:if test="${not empty errCode}">
		<h1>${errCode} : System Errors</h1>
	</c:if>
	
	<c:if test="${empty errCode}">
		<h1>System Errors</h1>
	</c:if>

	<c:if test="${not empty errMsg}">
		<h4>${errMsg}</h4>
	</c:if>
		
	</body>
</html>