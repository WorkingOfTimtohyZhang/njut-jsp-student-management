<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查数据是否为空
   if(add_teacher_form.teachername.value.length==0){
  		alert("输入的教师姓名为空，请重新输入！");
  		return false;
	}else return true;	
}
function delete_confirm() {
//按删除链接时，弹出确认对话框
   if(confirm("确认要删除吗？")){
  		return true;
	}else return false;	
}
-->
</script>
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<jsp:include page="navigator.txt"/>
<!---------数据输入------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%" colspan="3">录入教师信息：<br><hr></td>
        </tr>
        <form name="add_teacher_form" action="teacher_add.jsp" method="post" onsubmit="return check_data()">
        <tr>
          <td width="25%" align="right">教师姓名：</td>
          <td width="25%"><input type="text" name="teachername" maxlength="20"></td>
          <td width="50%"><input type="submit" value="提交"></td>
        </tr>
        </form>
      </table>
    </td>
  </tr>
</table><br>
<!---------数据输出------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
<jsp:useBean id="teacher_select" class="teacherman.teacher_operation" scope="page"/>
<%//-----用JavaBean查询出数据，并得到总记录条数------
	ResultSet rs=teacher_select.teacher_select_all();
	rs.last();
	int rowCount=rs.getRow();
	rs.beforeFirst();
%> 
<jsp:useBean id="teacher_rsFenYe" class="util.rsFenYe" scope="page"/>
<jsp:setProperty name="teacher_rsFenYe" property="rs" value="<%=rs%>"/>
<!-------pageSize为每页记录条数--------->
<jsp:setProperty name="teacher_rsFenYe" property="pageSize" value="10"/>
  <tr>
    <td colspan="4" align="center">
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
    	<%=teacher_rsFenYe.earn_fenyi_string("teacherman.jsp",new String[0],new String[0])%>
    </td>
  </tr>
  <tr>
    <td width="21%" align="center">教师序号</td>
    <td width="39%" align="center">教师姓名</td>
    <td width="20%" align="center">修改？</td>
    <td width="20%" align="center">删除？</td>
  </tr>
  <%for(int i=0;i<teacher_rsFenYe.getPageSize()&&rs.next();i++){%>
  <tr>
    <td align="center"><%=rs.getLong("teacher_id")%></td>
    <td align="center"><%=rs.getString("teacher_name")%></td>
    <td align="center"><a href='teacher_update1.jsp?teacher_id=<%=rs.getLong("teacher_id")%>'>修改</a></td>
    <td align="center">
    <a href="teacher_delete.jsp?teacher_id=<%=rs.getLong("teacher_id")%>" onclick="return delete_confirm()">×</a>
    </td>
  </tr>
  <%
  	}
  %>
</table><br>
<!----------操作提示信息--------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%">注意：删除教师信息将删除所有此教师的所有授课信息。</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>