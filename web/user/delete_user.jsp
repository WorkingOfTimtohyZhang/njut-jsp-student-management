<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="user.user_operation"%>
<%//-------接收参数------------
  	int sysuser_id=0;
  	try{
        sysuser_id=Integer.parseInt(request.getParameter("sysuser_id"));
    }catch(Exception e){}
    user_operation uop=new user_operation();
    uop.deleteUserByPrimKey(sysuser_id);
%>
<html>
<body bgcolor="#DCDADA">
	删除用户成功!
</body>
</html>
				 
				 
				 
				 
				 