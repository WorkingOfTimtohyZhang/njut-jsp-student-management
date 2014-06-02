<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet,util.stringUtil,user.user_operation"%>
<!--------接收输入参数---------->
<%//-------student_name参数------------
    String student_name=request.getParameter("student_name");
    if(student_name!=null&&student_name.equals("null")) 
    	student_name=null;
    stringUtil stringCode=new stringUtil();
    student_name=stringCode.codeToString(student_name);
%>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查数据是否为空
   if(student_form.studentname.value.length==0){
  		alert("输入的学生姓名为空，请重新输入！");
  		return false;
	}else return true;	
}
function confirm_genAll() {
//按生成所有学生用户按钮时，弹出确认对话框
   if(confirm("确认要生成所有学生用户吗？")){
  		window.navigate("auto_gen_users_save.jsp");
	}else return false;
}
function confirm_genPart() {
//按生成选中的部分学生用户按钮时，弹出确认对话框
   if(confirm("确认要生成选中的部分学生用户吗？")){
  		return true;
	}else return false;	
}
-->
</script>
<html>
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<table border="0" width="100%">
  <tr>
    <td width="100%">
      <p align="left">您当前所在位置：自动生成系统用户-->自动生成学生用户</p>
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
          <td width="100%" colspan="3">输入要查询的学生姓名：<br><hr></td>
        </tr>
        <form name="student_form" method="post" onsubmit="return check_data()">
        <tr>
          <td width="25%" align="right">学生姓名：</td>
          <td width="25%"><input type="text" name="student_name" maxlength="20"></td>
          <td width="50%"><input type="submit" value="提交"></td>
        </tr>
        </form>
      </table>
    </td>
  </tr>
</table><br>
<!---------数据输出------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <form name="gen_user_student" method="post" action="auto_gen_users_save.jsp">
  <tr>
    <td colspan="5" align="right">
		<input type="button" value="生成所有学生用户" name="genAll" onclick="return confirm_genAll()">
		<input type="submit" value="生成选中的学生用户" name="genPart" onclick="return confirm_genPart()">
    </td>
  </tr>
<jsp:useBean id="student_select" class="studentman.student_operation" scope="page"/>
<%//-----用JavaBean查询出数据，并得到总记录条数------
	ResultSet rs=student_select.student_select_part(0,student_name);
	rs.last();
	int rowCount=rs.getRow();
	rs.beforeFirst();
%> 
<jsp:useBean id="student_rsFenYe" class="util.rsFenYe" scope="page"/>
<jsp:setProperty name="student_rsFenYe" property="rs" value="<%=rs%>"/>
<!-------pageSize为每页记录条数--------->
<jsp:setProperty name="student_rsFenYe" property="pageSize" value="10"/>
  <tr>
    <td colspan="4" align="center">
<%//-----从请求参数中得到当前页码------
	String currentPage=request.getParameter("currentPage");
	try{
        student_rsFenYe.setCurrentPage(Integer.parseInt(currentPage));
    }catch(Exception e)
    {//如果参数不正确，设置当前页码为1
        student_rsFenYe.setCurrentPage(1);
    }
%>
    	所有学生(共<%=rowCount%>位)&nbsp;&nbsp;&nbsp;&nbsp;
    	<%
    	String paraName[]={"student_name"};
    	String paraValue[]={student_name};%>
    	<%=student_rsFenYe.earn_fenyi_string("auto_gen_users.jsp",paraName,paraValue)%>
    </td>
  </tr>
  <tr>
    <td width="15%" align="center">选择(<input type="checkbox" onclick="all_change()" name="allselect">全选)</td>
    <td width="15%" align="center">学生序号</td>
    <td width="30%" align="center">学生姓名</td>
    <td width="20%" align="center">是否生成了用户</td>
    <td width="20%" align="center">修改用户信息</td>
  </tr>
  <%int i=0;
  for(;i<student_rsFenYe.getPageSize()&&rs.next();i++){%>
  <tr>
    <td align="center">
       <input type="checkbox" name="check_user<%=i%>" value="<%=rs.getLong("student_id")%>">
    </td>
    <td align="center"><%=rs.getLong("student_id")%></td>
    <td align="center"><%=rs.getString("student_name")%></td>
    <td align="center">
    <%user_operation userOp=new user_operation();
      int haveGened=userOp.isAutoGenOK(rs.getLong("student_id"),4);
      switch(haveGened){
      	case 1:
      		out.println("已生成");break;
      	case 0:
      		out.println("未生成");break;
      	default:
      		out.println("未知");break;
      }
    %>
    </td>
    <td align="center">
    <%if(haveGened==1){
    	long user_id=userOp.getUserId(rs.getInt("student_id"),4);
    	out.print("<a href='update_users.jsp?sysuser_id="+
    			   user_id+"'>修改</a>&nbsp;"); 
    	out.print("<a href='delete_user.jsp?sysuser_id="+
    			   user_id+"'>删除</a>"); 
      }%>&nbsp;
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
          <td width="100%">注意：1.已生成用户的学生不能再次生成。<br>
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         2.生成的用户用户名为用户的真实姓名,密码为111111。</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
<!----复选框交互脚本---->
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function all_change() {
//点全选复选框时的交互
   if(gen_user_student.allselect.checked==true){
  		<%for(int j=0;j<i;j++){%>
  		gen_user_student.check_user<%=j%>.checked=true;
  		<%}%>
	}else{
	    <%for(int j=0;j<i;j++){%>
  		gen_user_student.check_user<%=j%>.checked=false;
  		<%}%>
	}
}
-->
</script>