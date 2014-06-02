<%@ page contentType="text/html;charset=GB2312" %>
<html>
  <body bgcolor="#DCDADA">
  <%//-------接收teacher_id参数------------
  	int teacher_id=0;
	try{
        teacher_id=Integer.parseInt(request.getParameter("teacher_id"));
    }catch(Exception e)
    {}
%>
    <jsp:useBean id="teacher_delete" class="teacherman.teacher_operation" scope="page"/>
    <%//-----根据结果提示操作信息 ---------
    	int deleteReturn=teacher_delete.teacher_delete(teacher_id);
    	switch(deleteReturn){
    		case 1:
    			out.print("删除教师数据成功!");break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 4:
    			out.print("输入数据非法!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="teacherman.jsp">返回</a>
  </body>
</html>
