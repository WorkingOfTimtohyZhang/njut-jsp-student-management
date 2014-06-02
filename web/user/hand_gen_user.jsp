<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet,util.stringUtil,user.user_operation,
				util.commonTag"%>
<!--------接收输入参数---------->
<%
    int teacher_id=0;
    int role_id=0;
	try{
        teacher_id=Integer.parseInt(request.getParameter("teacher_id"));
        role_id=Integer.parseInt(request.getParameter("role_id"));
    }catch(Exception e)
    {}
    user_operation userOp=new user_operation();
    userOp.genAdminUser(teacher_id,role_id);
%>
<html>
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<table border="0" width="100%">
  <tr>
    <td width="100%">
      <p align="left">您当前所在位置：手工生成系统用户</p>
    </td>
  </tr>
<jsp:include page="navigator.txt"/>
</table>
<!---------数据输入------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%" colspan="6">请选择要生成管理用户的教师及其角色：<br><hr></td>
        </tr>
        <form name="student_form" method="post" onsubmit="return check_data()">
        <tr>
          <td width="30%" align="right">&nbsp;</td>
          <td width="10%" align="right">教师：</td>
          <td width="10%">
          <%//-----生成教师下拉框------
          	commonTag classtag=new commonTag();
          	out.println(classtag.getTeacherTag(teacher_id));
          %>
          <td width="10%" align="right">角色：</td>
          <td width="10%">
          <%//-----生成角色下拉框------
          	out.println(classtag.getRoleTag());
          %>
          </td>
          <td width="30%"><input type="submit" value="提交"></td>
        </tr>
        </form>
      </table>
    </td>
  </tr>
</table><br>
<!---------数据输出------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
<jsp:useBean id="user_select" class="user.user_operation" scope="page"/>
<%//-----用JavaBean查询出数据，并得到总记录条数------
	ResultSet rs=user_select.getAdminUsers();
	rs.last();
	int rowCount=rs.getRow();
	rs.beforeFirst();
%> 
<jsp:useBean id="user_rsFenYe" class="util.rsFenYe" scope="page"/>
<jsp:setProperty name="user_rsFenYe" property="rs" value="<%=rs%>"/>
<!-------pageSize为每页记录条数--------->
<jsp:setProperty name="user_rsFenYe" property="pageSize" value="10"/>
  <tr>
    <td colspan="4" align="center">
<%//-----从请求参数中得到当前页码------
	String currentPage=request.getParameter("currentPage");
	try{
        user_rsFenYe.setCurrentPage(Integer.parseInt(currentPage));
    }catch(Exception e)
    {//如果参数不正确，设置当前页码为1
        user_rsFenYe.setCurrentPage(1);
    }
%>
    	所有管理用户(共<%=rowCount%>位)&nbsp;&nbsp;&nbsp;&nbsp;
    	<%
    	String paraName[]={"teacher_id"};
    	String paraValue[]={teacher_id+""};%>
    	<%=user_rsFenYe.earn_fenyi_string("hand_gen_user.jsp",paraName,paraValue)%>
    </td>
  </tr>
  <tr>
    <td width="20%" align="center">用户ID号</td>
    <td width="20%" align="center">用户名</td>
    <td width="20%" align="center">用户角色</td>
    <td width="40%" align="center">修改用户信息</td>
  </tr>
  <%int i=0;
  for(;i<user_rsFenYe.getPageSize()&&rs.next();i++){%>
  <tr>
    <td align="center">
       <%=rs.getLong("sysuser_id")%>
    </td>
    <td align="center"><%=rs.getString("sysuser_name")%></td>
    <td align="center">
    <%
      switch(rs.getInt("sysuser_role")){
      	case 1:
      		out.println("系统管理员");break;
      	case 2:
      		out.println("教务管理员");break;
      }
    %>
    </td>
     <td align="center">
    <%
    	long user_id=rs.getInt("sysuser_id");
    	out.print("<a href='update_userAdmin.jsp?sysuser_id="+
    			   user_id+"'>修改</a>&nbsp;"); 
    	out.print("<a href='delete_user.jsp?sysuser_id="+
    			   user_id+"'>删除</a>"); 
      %>&nbsp;
    </td>
  </tr>
  <%
  	}
  %>
  <input type="hidden" name="all_count" value="<%=i%>">
  </form>
</table><br>
<!----------操作提示信息--------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%">注意：1.已生成管理用户的教师不能再次生成。<br>
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         2.生成的管理用户用户名为用户的真实姓名,密码为111111。</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>