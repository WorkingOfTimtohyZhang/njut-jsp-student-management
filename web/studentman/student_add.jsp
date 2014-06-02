<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet,util.commonTag"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查输入数据
   if(add_student_form.class_id.value==0){
   		alert("请选择所属班级！");
   		return false;
   }
   if(add_student_form.studentname.value.length==0){
  		alert("输入的学生姓名为空，请重新输入！");
  		return false;
	}else return true;	
}
-->
</script>
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<table border="0" width="100%">
  <tr>
    <td width="100%">
      <p align="left">您当前所在位置：学生信息管理-->录入学生信息</p>
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
          <td width="100%" colspan="5">录入学生信息：<br><hr></td>
        </tr>
        <form name="add_student_form" action="student_add_insert.jsp" method="post" onsubmit="return check_data()">
        <tr>
          <td width="24%" align="right">学生所属班级：</td>
          <td width="19%">
          <%//-----生成学生所属班级下拉框------
          	commonTag classtag=new commonTag();
          	out.println(classtag.getClassTag(0));
          %>
          </td>
          <td width="11%" align="right">学生姓名：</td>
          <td width="21%"><input type="text" name="studentname" maxlength="20"></td>
          <td width="25%"><input type="submit" value="提交"></td>
        </tr>
        </form>
      </table>
    </td>
  </tr>
</table><br>
</body>
</html>