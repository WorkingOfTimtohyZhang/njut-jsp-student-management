<%@ page contentType="text/html;charset=GB2312" %>
<html>
  <body bgcolor="#DCDADA">
  <%//-------接收lession_id参数------------
  	int lession_id=0;
	try{
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
    }catch(Exception e)
    {}
%>
    <jsp:useBean id="lession_delete" class="lessionman.lession_operation" scope="page"/>
    <%//-----根据结果提示操作信息 ---------
    	int deleteReturn=lession_delete.lession_delete(lession_id);
    	switch(deleteReturn){
    		case 1:
    			out.print("删除课程数据成功!");break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 4:
    			out.print("输入数据非法!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="lessionman.jsp">返回</a>
  </body>
</html>
