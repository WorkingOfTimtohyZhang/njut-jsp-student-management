<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="util.stringUtil" %>
<html>
  <body bgcolor="#DCDADA">
    <jsp:useBean id="teacher_add" class="teacherman.teacher_operation" scope="page"/>
    <%  stringUtil stringCode=new stringUtil();
    	int addReturn=teacher_add.teacher_add_one(request.getParameter("teachername"));
    	switch(addReturn){
    		case 1:
    			out.print("增加教师数据成功!增加的教师姓名为:"+stringCode.codeToString(request.getParameter("teachername"))+"。");
    			break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 3:
    			out.print("此教师已存在!");break;
    		case 4:
    			out.print("输入数据为空!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="teacherman.jsp">返回</a>
  </body>
</html>
