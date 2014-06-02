<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查数据是否为空
   if(update_teacher_form.teachername.value.length==0){
  		alert("输入的教师姓名为空，请重新输入！");
  		return false;
	}else return true;	
}
-->
</script>
<%//-------接收teacher_id参数------------
  	int teacher_id=0;
	try{
        teacher_id=Integer.parseInt(request.getParameter("teacher_id"));
    }catch(Exception e)
    {}
%>
<!----声明JavaBean，并查询出数据---->
<jsp:useBean id="teacher_select" class="teacherman.teacher_operation" scope="page"/>
<%ResultSet rs=teacher_select.teacher_select_one(teacher_id);%>
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<jsp:include page="navigator.txt"/>
<%if(rs.next()){%>
<!---------数据输入------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%" colspan="3">修改教师信息：<br><hr></td>
        </tr>
        <form name="update_teacher_form" action="teacher_update2.jsp" method="post" onsubmit="return check_data()">
        <tr>
          <td width="25%" align="right">教师序号：<%=rs.getLong("teacher_id")%>&nbsp;&nbsp;教师姓名：</td>
          <input type="hidden" value="<%=rs.getLong("teacher_id")%>" name="teacher_id">
          <td width="25%"><input type="text" name="teacher_name" value="<%=rs.getString("teacher_name")%>" maxlength="20"></td>
          <td width="50%"><input type="submit" value="提交"></td>
        </tr>
        </form>
      </table>
    </td>
  </tr>
</table>
<%}%>
<br>
</body>
</html>