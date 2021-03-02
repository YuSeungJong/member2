<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
			request.getContentType() : 리턴값을 출력 <br>
			request.getMethod() : 리턴값을 출력 <br>
			request.getRequestURI() : 리턴값을 출력<br>
			request.getContextPath() : 리턴값을 출력<br>
			request.getServerPort() : 리턴값을 출력<br>
	webapp/index.jsp ==> localhost/index.jsp
	
	webapp/image/brown.png
	<img src="${cp }/image/brown.png" />
</body>
</html>