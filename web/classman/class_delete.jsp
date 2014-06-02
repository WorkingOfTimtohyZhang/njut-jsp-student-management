u<%@ page contentType="text/html;charset=GB2312" %>
<html>
  <body bgcolor="#DCDADA">
  <%//-------接收class_id参数------------
  	int class_id=0;
	try{
        class_id=Integer.parseInt(request.getParameter("class_id"));
    }catch(Exception e)
    {}
%>
    <jsp:useBean id="class_delete" class="classman.class_operation" scope="page"/>
    <%//-----根据结果提示操作信息------
    	int deleteReturn=class_delete.class_delete(class_id);
    	switch(deleteReturn){
    		case 1:
    			out.print("删除班级数据成功!");break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 4:
    			out.print("输入数据非法!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="class_add.jsp">返回</a>
  </body>
</html>
