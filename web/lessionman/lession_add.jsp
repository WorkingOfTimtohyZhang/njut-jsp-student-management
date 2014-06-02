<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="util.stringUtil" %>
<html>
  <body bgcolor="#DCDADA">
    <jsp:useBean id="lession_add" class="lessionman.lession_operation" scope="page"/>
    <%  stringUtil stringCode=new stringUtil();
    	int addReturn=lession_add.lession_add_one(request.getParameter("lessionname"));
    	switch(addReturn){
    		case 1:
    			out.print("增加课程数据成功!增加的课程名为:"+stringCode.codeToString(request.getParameter("lessionname"))+"。");
    			break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 3:
    			out.print("此课程已存在!");break;
    		case 4:
    			out.print("输入数据为空!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="lessionman.jsp">返回</a>
  </body>
</html>
