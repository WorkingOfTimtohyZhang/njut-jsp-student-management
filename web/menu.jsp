<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet,user.user_operation,
				 java.sql.SQLException"%>
<%//-------接收输入参数------------
  	int sysuser_id=0;
  	int sysuser_role=0;
  	ResultSet rs=null;
	try{
        sysuser_id=Integer.parseInt((String)session.getAttribute("sysuser_id"));
    }catch(Exception e){}
	user_operation uop=new user_operation();
	rs=uop.getUserByPrimKey(sysuser_id);
	String sysuser_name=null;
	int rowCount=0;
	try{
		rs.next();
		rowCount=1;
	}catch(Exception e){}
	if(rowCount!=0){
		sysuser_role=rs.getInt("sysuser_role");
		sysuser_name=rs.getString("sysuser_name");
	}
%>
<html>
<body leftMargin="0" topMargin="0" marginheight="0" marginwidth="0" bgcolor="#DCDADA">
<table border="0" width="90%" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
    <td align="left">欢迎您:<%=sysuser_name%></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td align="left">您的角色：
    <%
    if(sysuser_role==1) out.print("系统管理员");
    if(sysuser_role==2) out.print("教务管理员");
    if(sysuser_role==3) out.print("教师用户");
    if(sysuser_role==4) out.print("学生用户");
    %>
    </td>
  </tr>
  
  <tr>
    <td>&nbsp;</td>
    <td align="center">&nbsp;</td>
  </tr>
  <%if(sysuser_role==1||sysuser_role==2){%>
  <tr>
    <td>&nbsp;</td>
    <td align="left">
    	<font color="#CC6600"><u>基础数据管理</u></font>
    	<li><a href="classman/class_add.jsp" target="main">班级信息管理</a></li><br>
        <li><a href="teacherman/teacherman.jsp" target="main">教师信息管理</a></li><br>
        <li><a href="lessionman/lessionman.jsp" target="main">课程信息管理</a></li><br>
        <li><a href="studentman/studentman.jsp" target="main">学生信息管理</a></li> <br>
        <br>
    </td>
  </tr>
  <%}%>
  <%if(sysuser_role==1||sysuser_role==2){%>
  <tr>
    <td>&nbsp;</td>
    <td align="left">
    	<font color="#CC6600"><u>教务管理</font></u>
    	<li><a href="teachlessionman/teachlessionman.jsp" target="main">教师授课信息管理</a></li><br>
        <li><a href="core/core_sa_pie.jsp" target="main">学生成绩分析</a></li><br>
        <li><a href="core/core_close.jsp" target="main">学生成绩封存</a></li><br>
        <br>
    </td>
  </tr>
  <%}%>
  <%if(sysuser_role==1||sysuser_role==2||sysuser_role==3){%>
  <tr>
    <td>&nbsp;</td>
    <td align="left">
    	<font color="#CC6600"><u>成绩录入</font></u>
    	<li><a href="core/core_add.jsp" target="main">学生成绩录入</a></li><br>
    	<br>
    </td>
  </tr>
  <%}%>
  <%if(sysuser_role==1||sysuser_role==2||sysuser_role==3||sysuser_role==4){%>
  <tr>
    <td>&nbsp;</td>
    <td align="left">
    	<font color="#CC6600"><u>成绩查询</u></font>
    	<li><a href="core/student_core_view.jsp" target="main">学生成绩查询</a></li><br>
    	<br>
    </td>
  </tr>
  <%}%>
  <tr>
    <td>&nbsp;</td>
    <td align="left">
    	<font color="#CC6600"><u>系统管理</u></font>
    	<%if(sysuser_role==1){%>
    	<li><a href="user/auto_gen_user.jsp" target="main">自动生成系统用户</a></li><br>
    	<li><a href="user/hand_gen_user.jsp" target="main">手工生成系统用户</a></li><br>
    	<%}%>
    	<%if(sysuser_role==1||sysuser_role==2||sysuser_role==3||sysuser_role==4){%>
    	<li><a href="user/modi_user_pass.jsp?sysuser_id=<%=session.getAttribute("sysuser_id")%>" target="main">修改当前用户密码</a></li><br>
    	<%}%>
    	<br>
    </td>
  </tr>
</table>
</body>
</html>