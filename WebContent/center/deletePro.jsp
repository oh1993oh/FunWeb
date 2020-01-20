<%@page import="board.BoardBean"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello World</title>
</head>
<body>
<%
int num = Integer.parseInt(request.getParameter("num"));
String pageNum = request.getParameter("pageNum");
String name = request.getParameter("name");

BoardDAO bdao = new BoardDAO();

BoardBean bb = new BoardBean();

//자바빈 멤버변수 <= 파라미터 저장
bb.setNum(num);
bb.setName(name);

//세션값 가져오기
String id = (String)session.getAttribute("id");

//세션값이 존재하고 세션값의 글쓴이가 일치하면 글수정 할 수 있게 하기
if(id != null && id.equals(bb.getName())) {
 
 bdao.deleteBoard(bb);
 %>
 <script type="text/javascript">
     alert("글삭제 완료");
     location.href ="notice.jsp?num=<%=bb.getNum()%>&pageNum=<%=pageNum %>";
 </script>
 <%
}
else {  
 %>
 <script type="text/javascript">
 alert("삭제 불가");
 history.back();
 </script>
 <%
}
%>
</body>
</html>