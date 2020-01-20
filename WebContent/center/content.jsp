<%@page import="java.text.SimpleDateFormat"%>
<%@page import="board.BoardBean"%>
<%@page import="java.util.List"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js" type="text/javascript"></script>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/ie7-squish.js" type="text/javascript"></script>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if IE 6]>
 <script src="../script/DD_belatedPNG_0.0.8a.js"></script>
 <script>
   /* EXAMPLE */
   DD_belatedPNG.fix('#wrap');
   DD_belatedPNG.fix('#main_img');   

 </script>
 <![endif]-->
</head>
<body>
<div id="wrap">
<!-- 헤더들어가는 곳 -->
<jsp:include page="../inc/top.jsp" />
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 메인이미지 -->
<div id="sub_img_center"></div>
<!-- 메인이미지 -->

<!-- 왼쪽메뉴 -->
<nav id="sub_menu">
<ul>
<li><a href="#">Notice</a></li>
<li><a href="#">Public News</a></li>
<li><a href="#">Driver Download</a></li>
<li><a href="#">Service Policy</a></li>
</ul>
</nav>
<!-- 왼쪽메뉴 -->
<%
// int num  파라미터 가져오기
int num=Integer.parseInt(request.getParameter("num"));
// String pageNum 파라미터 가져오기
String pageNum=request.getParameter("pageNum");
//BoardDAO bdao 객체생성  
BoardDAO bdao=new BoardDAO();
//updateReadcount(num)
bdao.updateReadcount(num);
// num에 해당하는 게시판 글 가져오기 
// BoardBean bb = getBoard(num) 
BoardBean bb=bdao.getBoard(num);
// content   엔터키  "\r\n"  =>  "<br>"
String content=bb.getContent();
if(content!=null){
	content=content.replace("\r\n","<br>");
}
%>
<!-- 게시판 -->
<article>
<h1>Notice Content</h1>
<table id="notice">
<tr><th class="tno">글번호</th><td><%=bb.getNum() %></td>
    <th class="tno">글쓴날짜</th><td><%=bb.getDate() %></td></tr>
<tr><th class="tno">글쓴이</th><td><%=bb.getName() %></td>
    <th class="tno">조회수</th><td><%=bb.getReadcount() %></td></tr>
<tr><th class="tno">제목</th><td colspan="3"><%=bb.getSubject() %></td></tr>
<tr><th class="tno">첨부파일</th>
<td colspan="3">
<a href="../upload/<%=bb.getFile()%>"><%=bb.getFile() %></a></td></tr>
<tr><th class="tno">내용</th><td colspan="3"><%=content %></td></tr> 
</table>
<div id="table_search">
<%
// 세션값 가져오기
String id=(String)session.getAttribute("id");
// 세션값 있으면
//   세션값  글쓴이 일치하면  글수정 글삭제 버튼 보이기
if(id!=null){
	if(id.equals(bb.getName())){
		%>
<input type="button" value="글수정" class="btn"
onclick="location.href='updateForm.jsp?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">
<input type="button" value="글삭제" class="btn"
onclick="location.href='deleteForm.jsp?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">
		<%
	}
}
%>
<input type="button" value="글목록" class="btn" 
onclick="location.href='notice.jsp?pageNum=<%=pageNum%>'">
</div>
<div class="clear"></div>
<div id="page_control">
</div>
</article>
<!-- 게시판 -->
<!-- 본문들어가는 곳 -->
<div class="clear"></div>
<!-- 푸터들어가는 곳 -->
<jsp:include page="../inc/bottom.jsp" />
<!-- 푸터들어가는 곳 -->
</div>
</body>
</html>