<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="util.commonTag"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查输入数据
   if(add_teachlession_form.teacher_id.value==0){
   		alert("请选择教师！");
   		return false;
   }
   if(add_teachlession_form.lession_id.value==0){
   		alert("请选择课程！");
   		return false;
   }
}
-->
</script>
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<table border="0" width="100%">
  <tr>
    <td width="100%">
      <p align="left">您当前所在位置：教师授课信息管理-->录入教师授课信息</p>
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
          <td width="100%" colspan="5">录入教师授课信息：<br><hr></td>
        </tr>
        <form name="add_teachlession_form" action="teachlession_add_insert.jsp" method="post" onsubmit="return check_data()">
        <tr>
          <td width="30%" align="right">教师姓名：</td>
          <td width="10%">
          <%//-----生成教师下拉框------
          	commonTag classtag=new commonTag();
          	out.println(classtag.getTeacherTag(0));
          %>
          </td>
          <td width="10%" align="right">课程名：</td>
          <td width="20%">
          <%//-----生成课程下拉框------
          	out.println(classtag.getLessionTag(0));
          %>
          </td>
          <td width="30%"><input type="submit" value="提交"></td>
        </tr>
        </form>
      </table>
    </td>
  </tr>
</table><br>
</body>
</html>