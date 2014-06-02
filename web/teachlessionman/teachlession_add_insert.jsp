<%@ page contentType="text/html;charset=GB2312" %>
<html>
<%//-------接收teacher_id,lession_id参数------------
  	int teacher_id=0;
  	int lession_id=0;
	try{
        teacher_id=Integer.parseInt(request.getParameter("teacher_id"));
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
    }catch(Exception e)
    {}
%>
  <body bgcolor="#DCDADA">
    <jsp:useBean id="teachlession_add" class="teachlessionman.teachlession_operation" scope="page"/>
    <%  
    	int addReturn=teachlession_add.teachlession_add_one(lession_id,teacher_id);
    	switch(addReturn){
    		case 1:
    			out.print("增加教师授课数据成功!");break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 3:
    			out.print("此教师授课数据已存在!");break;
    		case 4:
    			out.print("输入数据为空!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="teachlessionman.jsp">返回</a>
  </body>
</html>
 