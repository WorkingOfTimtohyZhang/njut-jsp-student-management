<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet,util.stringUtil,user.user_operation"%>
<!--------接收输入参数---------->
<%//-------teacher_name参数------------
    String teacher_name=request.getParameter("teacher_name");
    if(teacher_name!=null&&teacher_name.equals("null")) 
    	teacher_name=null;
    stringUtil stringCode=new stringUtil();
    teacher_name=stringCode.codeToString(teacher_name);
%>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查数据是否为空
   if(teacher_form.teachername.value.length==0){
  		alert("输入的教师姓名为空，请重新输入！");
  		return false;
	}else return true;	
}
function confirm_genAll() {
//按生成所有教师用户按钮时，弹出确认对话框
   if(confirm("确认要生成所有教师用户吗？")){
  		window.navigate("auto_gen_user_save.jsp");
	}else return false;
}
function confirm_genPart() {
//按生成选中的部分教师用户按钮时，弹出确认对话框
   if(confirm("确认要生成选中的部分教师用户吗？")){
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
      <p align="left">您当前所在位置：自动生成系统用户-->自动生成教师用户</p>
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
          <td width="100%" colspan="3">输入要查询的老师姓名：<br><hr></td>
        </tr>
        <form name="teacher_form" method="post" onsubmit="return check_data()">
        <tr>
          <td width="25%" align="right">教师姓名：</td>
          <td width="25%"><input type="text" name="teacher_name" maxlength="20"></td>
          <td width="50%"><input type="submit" value="提交"></td>
        </tr>
        </form>
      </table>
    </td>
  </tr>
</table><br>
<!---------数据输出------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <form name="gen_user_teahcer" method="post" action="auto_gen_user_save.jsp">
  <tr>
    <td colspan="5" align="right">
		<input type="button" value="生成所有教师用户" name="genAll" onclick="return confirm_genAll()">
		<input type="submit" value="生成选中的教师用户" name="genPart" onclick="return confirm_genPart()">
    </td>
  </tr>
<jsp:useBean id="teacher_select" class="teacherman.teacher_operation" scope="page"/>
<%//-----用JavaBean查询出数据，并得到总记录条数------
	ResultSet rs=teacher_select.teacher_select_part_by_name(teacher_name);
	rs.last();
	int rowCount=rs.getRow();
	rs.beforeFirst();
%> 
<jsp:useBean id="teacher_rsFenYe" class="util.rsFenYe" scope="page"/>
<jsp:setProperty name="teacher_rsFenYe" property="rs" value="<%=rs%>"/>
<!-------pageSize为每页记录条数--------->
<jsp:setProperty name="teacher_rsFenYe" property="pageSize" value="10"/>
  <tr>
    <td colspan="5" align="center">
<%//-----从请求参数中得到当前页码------
	String currentPage=request.getParameter("currentPage");
	try{
        teacher_rsFenYe.setCurrentPage(Integer.parseInt(currentPage));
    }catch(Exception e)
    {//如果参数不正确，设置当前页码为1
        teacher_rsFenYe.setCurrentPage(1);
    }
%>
    	所有教师(共<%=rowCount%>位)&nbsp;&nbsp;&nbsp;&nbsp;
    	<%
    	String paraName[]={"teacher_name"};
    	String paraValue[]={teacher_name};%>
    	<%=teacher_rsFenYe.earn_fenyi_string("auto_gen_user.jsp",paraName,paraValue)%>
    </td>
  </tr>
  <tr>
    <td width="15%" align="center">选择(<input type="checkbox" onclick="all_change()" name="allselect">全选)</td>
    <td width="15%" align="center">教师序号</td>
    <td width="30%" align="center">教师姓名</td>
    <td width="20%" align="center">是否生成了用户</td>
    <td width="20%" align="center">修改用户信息</td>
  </tr>
  <%int i=0;
  for(;i<teacher_rsFenYe.getPageSize()&&rs.next();i++){%>
  <tr>
    <td align="center">
       <input type="checkbox" name="check_user<%=i%>" value="<%=rs.getLong("teacher_id")%>">
    </td>
    <td align="center"><%=rs.getLong("teacher_id")%></td>
    <td align="center"><%=rs.getString("teacher_name")%></td>
    <td align="center">
    <%user_operation userOp=new user_operation();
      int haveGened=userOp.isAutoGenOK(rs.getLong("teacher_id"),3);
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
    	long user_id=userOp.getUserId(rs.getInt("teacher_id"),3);
    	out.print("<a href='update_user.jsp?sysuser_id="+
    			   user_id+"'>修改</a>&nbsp;"); 
    	out.print("<a href='delete_user.jsp?sysuser_id="+
    			   user_id+"'>删除</a>"); 
      }%>&nbsp;
    </td>
    </tr>
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
          <td width="100%">注意：1.已生成用户的教师不能再次生成。<br>
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         2.生成的用户用户名为用户的真实姓名,密码为111111。<br>
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         3.未生成用户的信息是不能修改的。
         </td>
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
   if(gen_user_teahcer.allselect.checked==true){
  		<%for(int j=0;j<i;j++){%>
  		gen_user_teahcer.check_user<%=j%>.checked=true;
  		<%}%>
	}else{
	    <%for(int j=0;j<i;j++){%>
  		gen_user_teahcer.check_user<%=j%>.checked=false;
  		<%}%>
	}
}
-->
</script>