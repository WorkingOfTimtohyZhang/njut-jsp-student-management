<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet,util.commonTag"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查数据是否为空
   if(update_student_form.class_id.value==0){
   		alert("请选择学生所属班级！");
   		return false;
   }
   if(update_student_form.studentname.value.length==0){
  		alert("输入的学生姓名为空，请重新输入！");
  		return false;
	}else return true;	
}
-->
</script>
<%//-------接收student_id,接收class_id参数参数------------
  	int student_id=0;
  	int class_id=0;
	try{
        student_id=Integer.parseInt(request.getParameter("student_id"));
        class_id=Integer.parseInt(request.getParameter("class_id"));
    }catch(Exception e)
    {}
%>
<!----声明JavaBean，并查询出数据---->
<jsp:useBean id="student_select" class="studentman.student_operation" scope="page"/>
<%ResultSet rs=student_select.student_select_one(student_id);%>
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<jsp:include page="navigator.txt"/>
<%if(rs.next()){%>
<!---------数据输入------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="103%">
        <tr>
          <td width="100%" colspan="5">修改学生信息：<br><hr></td>
        </tr>
        <form name="update_student_form" action="student_update2.jsp" method="post" onsubmit="return check_data()">
        <tr>
          <td width="30%" align="right">学生序号：<%=rs.getLong("student_id")%>&nbsp;&nbsp;所属班级：</td>
          <input type="hidden" value="<%=rs.getLong("student_id")%>" name="student_id">
          <td width="20%">
          <%//-----生成学生所属班级下拉框------
          	commonTag classtag=new commonTag();
          	out.println(classtag.getClassTag(class_id));
          %>
          </td>
          <td width="10%" align="right">学生姓名：</td>
          <td width="20%"><input type="text" name="student_name" value="<%=rs.getString("student_name")%>" maxlength="20"></td>
          <td width="20%"><input type="submit" value="提交"></td>
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