<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="util.stringUtil" %>
<html>
<%//-------接收class_id参数------------
  	int class_id=0;
	try{
        class_id=Integer.parseInt(request.getParameter("class_id"));
    }catch(Exception e)
    {}
%>
  <body bgcolor="#DCDADA">
    <jsp:useBean id="student_add" class="studentman.student_operation" scope="page"/>
    <%  stringUtil stringCode=new stringUtil();
    	int addReturn=student_add.student_add_one(request.getParameter("studentname"),class_id);
    	switch(addReturn){
    		case 1:
    			out.print("增加学生数据成功!增加的学生姓名为:"+stringCode.codeToString(request.getParameter("studentname"))+"。");
    			break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 3:
    			out.print("此学生已存在!");break;
    		case 4:
    			out.print("输入数据为空!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="studentman.jsp">返回</a>
  </body>
</html>
 