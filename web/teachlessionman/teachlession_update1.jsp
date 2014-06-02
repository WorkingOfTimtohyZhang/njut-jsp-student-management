<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet,util.commonTag"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查数据是否为空
   if(update_teachlession_form.teacher_id.value==0){
  		alert("请选择教师！");
  		return false;
	}
	if(update_teachlession_form.lession_id.value==0){
  		alert("请选择课程！");
  		return false;
	}
}
-->
</script>
<%//-------接收teachlession_id,teacher_id,lession_id参数------------
  	int teachlession_id=0;
  	int teacher_id=0;
  	int lession_id=0;
	try{
        teachlession_id=Integer.parseInt(request.getParameter("teachlession_id"));
        teacher_id=Integer.parseInt(request.getParameter("teacher_id"));
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
    }catch(Exception e)
    {}
%>
<!----声明JavaBean，并查询出数据---->
<jsp:useBean id="teachlession_select" class="teachlessionman.teachlession_operation" scope="page"/>
<%ResultSet rs=teachlession_select.teachlession_select_one(teachlession_id);%>
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
        <form name="update_teachlession_form" action="teachlession_update2.jsp" method="post" onsubmit="return check_data()">
        <tr>
          <td width="40%" align="right">教师授课信息序号：<%=rs.getLong("teachlession_id")%>&nbsp;&nbsp;授课教师：</td>
          <input type="hidden" value="<%=rs.getLong("teachlession_id")%>" name="teachlession_id">
          <td width="10%">
          <%//-----生成授课教师下拉框------
          	commonTag classtag=new commonTag();
          	out.println(classtag.getTeacherTag(teacher_id));
          %>
          </td>
          <td width="10%" align="right">课程名称：</td>
          <td width="20%">
          <%//-----生成授课教师下拉框------
          	out.println(classtag.getLessionTag(lession_id));
          %>
          </td>
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