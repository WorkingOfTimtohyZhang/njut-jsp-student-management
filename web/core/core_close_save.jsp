<%@ page contentType="text/html;charset=GB2312" %>
<html>
  <body bgcolor="#DCDADA">
  <%//-------接收class_id和lession_id参数------------
  	int class_id=0;
  	int lession_id=0;
	try{
        class_id=Integer.parseInt(request.getParameter("class_id"));
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
    }catch(Exception e)
    {}
%>
    <jsp:useBean id="classlession_update" class="lessionman.classlession_operation" scope="page"/>
    <%//-----根据结果提示操作信息----------- 
    	int updateReturn=classlession_update.classLessionCloseSave(class_id,lession_id);
    	switch(updateReturn){
    		case 1:
    			out.print("修改状态成功!");break;
    		case 2:
    			out.print("数据库操作失败!");break;
    		case 4:
    			out.print("输入数据非法!");break;
    		default:
    			out.print("操作失败！");
    	}
    %>
    <br><a href="core_close.jsp">返回</a>
  </body>
</html>