<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查数据是否为空
   if(add_class_form.classname.value.length==0){
  		alert("输入的班级名称为空，请重新输入！");
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
          <td width="100%" colspan="3">录入班级信息：<br><hr></td>
        </tr>
        <form name="add_class_form" action="class_add_insert.jsp" method="post" onsubmit="return check_data()">
        <tr>
          <td width="25%" align="right">班级名称：</td>
          <td width="25%"><input type="text" name="classname" maxlength="20"></td>
          <td width="50%"><input type="submit" value="提交"></td>
        </tr>
        </form>
      </table>
    </td>
  </tr>
</table><br>
<!---------数据输出------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
<jsp:useBean id="class_select" class="classman.class_operation" scope="page"/>
<%//-----用JavaBean查询出数据，并得到总记录条数------
	ResultSet rs=class_select.class_select_all();
	rs.last();
	int rowCount=rs.getRow();
	rs.beforeFirst();
%> 
<jsp:useBean id="class_rsFenYe" class="util.rsFenYe" scope="page"/>
<jsp:setProperty name="class_rsFenYe" property="rs" value="<%=rs%>"/>
<!-------pageSize为每页记录条数--------->
<jsp:setProperty name="class_rsFenYe" property="pageSize" value="10"/>
  <tr>
    <td colspan="4" align="center">
<%//-----从请求参数中得到当前页码------
	String currentPage=request.getParameter("currentPage");
	try{
        class_rsFenYe.setCurrentPage(Integer.parseInt(currentPage));
    }catch(Exception e)
    {//如果参数不正确，设置当前页码为1
        class_rsFenYe.setCurrentPage(1);
    }
%>
    	所有班级(共<%=rowCount%>个)&nbsp;&nbsp;&nbsp;&nbsp;
    	<%=class_rsFenYe.earn_fenyi_string("class_add.jsp",new String[0],new String[0])%>
    </td>
  </tr>
  <tr>
    <td width="21%" align="center">班级序号</td>
    <td width="39%" align="center">班级名称</td>
    <td width="20%" align="center">修改？</td>
    <td width="20%" align="center">删除？</td>
  </tr>
  <%for(int i=0;i<class_rsFenYe.getPageSize()&&rs.next();i++){%>
  <tr>
    <td align="center"><%=rs.getLong("class_id")%></td>
    <td align="center"><%=rs.getString("class_name")%></td>
    <td align="center"><a href='class_update1.jsp?class_id=<%=rs.getLong("class_id")%>'>修改</a></td>
    <td align="center">
    <a href="class_delete.jsp?class_id=<%=rs.getLong("class_id")%>" onclick="return delete_confirm()">×</a>
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
          <td width="100%">注意：删除班级信息将删除所有此班的所有学生信息，此班所有学生的成绩数据，此班的所有开课信息。</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>