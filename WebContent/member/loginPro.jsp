<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- member/loginPro.jsp -->
<%
// 한글처리
request.setCharacterEncoding("utf-8");
// 파라미터 가져오기
String id=request.getParameter("id");
String pass=request.getParameter("pass");


// MemberDAO mdao 객체생성
MemberDAO mdao=new MemberDAO();
// int check = userCheck(id,pass)메서드호출
int check=mdao.userCheck(id, pass);
// check==1 세션값 생성 "id",id  main.jsp 이동
// check==0 "비밀번호틀림" 뒤로이동
// check==-1 "아이디없음" 뒤로이동
if(check==1){
	session.setAttribute("id", id);
	response.sendRedirect("../main/main.jsp");
}else if(check==0){
	%>
	<script type="text/javascript">
		alert("비밀번호틀림");
		history.back();
	</script>
	<%
}else{
	%>
	<script type="text/javascript">
		alert("아이디없음");
		history.back();
	</script>
	<%
}
	
%>
</body>
</html>