<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="util.stringUtil" %>
<html>
  <body bgcolor="#DCDADA">
    <jsp:useBean id="class_add" class="classman.class_operation" scope="page"/>
    <%  stringUtil stringCode=new stringUtil();
    	int addReturn=class_add.class_add_one(request.getParameter("classname"));
    	switch(addReturn){
    		case 1:
    			out.print("增加班级数据成功!增加的班级名称为:"+stringCode.codeToString(request.getParameter("classname"))+"。");
    			break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 3:
    			out.print("此班级已存在!");break;
    		case 4:
    			out.print("输入数据为空!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="class_add.jsp">返回</a>
  </body>
</html>
