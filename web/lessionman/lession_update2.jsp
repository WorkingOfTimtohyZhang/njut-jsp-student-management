<%@ page contentType="text/html;charset=GB2312" %>
<html>
  <body bgcolor="#DCDADA">
  <%//-------接收lession_id参数------------
  	int lession_id=0;
	try{
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
    }catch(Exception e)
    {}
    String lession_name=request.getParameter("lession_name");
%>
    <jsp:useBean id="lession_update" class="lessionman.lession_operation" scope="page"/>
    <%//-----根据结果提示操作信息----------- 
    	int updateReturn=lession_update.lession_update(lession_id,lession_name);
    	switch(updateReturn){
    		case 1:
    			out.print("更新课程数据成功!");break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 3:
    			out.print("此课程已存在!");break;
    		case 4:
    			out.print("输入数据非法!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="lessionman.jsp">返回</a>
  </body>
</html>