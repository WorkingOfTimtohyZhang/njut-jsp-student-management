<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="user.user_operation"%>
<html>
  <body bgcolor="#DCDADA">
<!--------接收输入参数---------->
<%//-------接收all_count参数------------
  	int all_count=0;
	try{
        all_count=Integer.parseInt(request.getParameter("all_count"));
    }catch(Exception e)
    {} 
    //-----接收要生成的教师ID号-------
    String genTeacherId[]=new String[all_count];
    String tempString=new String("");
    for(int i=0;i<all_count;i++){
    	tempString=request.getParameter("check_user"+i);
    	if(tempString==null||tempString.length()==0||tempString.equalsIgnoreCase("null"))
    		genTeacherId[i]=null;
    	else
    		genTeacherId[i]=tempString;
    }
    user_operation userOp=new user_operation();
    String viewString=userOp.genTeacherUser(genTeacherId);
    out.println(viewString);
%>
    <br><a href="auto_gen_user.jsp">返回</a>
  </body>
</html>