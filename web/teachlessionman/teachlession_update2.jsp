<%@ page contentType="text/html;charset=GB2312" %>
<html>
  <body bgcolor="#DCDADA">
<%//-------接收teachlession_id,teacher_id,lession_id参数------------
  	int teachlession_id=0;
  	int teacher_id=0;
  	int lession_id=0;
	try{
        teachlession_id=Integer.parseInt(request.getParameter("teachlession_id"));
        teacher_id=Integer.parseInt(request.getParameter("teacher_id"));
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
    }catch(Exception e)
    {}
%>
    <jsp:useBean id="teachlession_update" class="teachlessionman.teachlession_operation" scope="page"/>
    <%//-----根据结果提示操作信息----------- 
    	int updateReturn=teachlession_update.teachlession_update(teachlession_id,lession_id,teacher_id);
    	switch(updateReturn){
    		case 1:
    			out.print("更新教师授课数据成功!");break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 3:
    			out.print("此教师授课数据已存在!");break;
    		case 4:
    			out.print("输入数据非法!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="teachlessionman.jsp">返回</a>
  </body>
</html>