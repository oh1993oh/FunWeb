<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function result() {
	// join.jsp id상자에 <= 검색한 id 넣어줌
	opener.document.fr.id.value = document.wfr.userid.value;
	// 창닫기
	window.close();
}
</script>
</head>
<body>
<h1>WebContent/member/join_idcheck.jsp</h1>
<%
// userid 파라미터 가져오기
String id = request.getParameter("userid");
// 출력
// out.println(id);
// MemberDAO 객체생성
MemberDAO mdao = new MemberDAO();
// int check = idcheck(id) 메서드호출
int check = mdao.idCheck(id);
// check == 1 아이디존재 "아이디중복"
if(check == 1){
	out.println(id+" 아이디 중복");
}else if(check == 0){
	// check == 0 아이디없음 "아이디 사용가능"
	out.println(id+" 아이디 사용가능");
	%>
	<input type = "button" value = "아이디사용" onclick="result()">
	<%
}

%>
<form action="join_idcheck.jsp" method = "post" name = "wfr">
아이디 : <input type = "text" name = "userid" value = "<%=id %>">
<input type = "submit" value = "아이디 중복확인">
</form>
</body>
</html>ml>