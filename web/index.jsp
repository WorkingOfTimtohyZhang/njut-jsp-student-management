<%@ page contentType="text/html;charset=GB2312" %>
<%@ include file="common/session_check.jsp"%>
<html>
<head>
<title>学生成绩管理系统</title>
</head>
<frameset framespacing="1" rows="69,*" frameborder="0" name="all">
  <frame name="banner" scrolling="no" noresize target="contents" src="banner.htm">
  <frameset cols="178,*">
    <frame name="menu" target="main" src="menu.jsp" marginwidth="0" marginheight="0" scrolling="no" noresize>
    <frame src="firstPage.jsp" name="main" scrolling="auto">
  </frameset>
  <noframes>
  <body>
  </body>
  </noframes>
</frameset>
</html>
