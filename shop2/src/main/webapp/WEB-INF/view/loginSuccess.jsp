<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 결과 화면</title>
</head>
<body>
<h2> 환영합니다. ${sessionScope.loginUser.username} 님이 로그인 하셨습니다.</h2>
<table border="1" style="border-collapse:collapse;">
	<tr><td>아이디</td><td>${sessionScope.loginUser.userid}</td></tr>
	<tr><td>이름</td><td>${sessionScope.loginUser.username }</td></tr>
	<tr><td>우편번호</td><td>${loginUser.postcode }</td></tr> <%--${sessionScope.loginUser.postcode } 이 정석 --%>
	<tr><td>주소</td><td>${loginUser.address }</td></tr>
	<tr><td>전화번호</td><td>${loginUser.phoneno }</td></tr>
	<tr><td>이메일</td><td>${loginUser.email }</td></tr>
	<tr><td>생년월일</td><td><fmt:formatDate value="${loginUser.birthday }" pattern="yyyy년MM월dd일 "/></td></tr>
	</table>
</body>
</html>