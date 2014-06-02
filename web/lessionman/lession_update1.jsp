<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查数据是否为空
   if(update_lession_form.lessionname.value.length==0){
  		alert("输入的课程姓名为空，请重新输入！");
  		return false;
	}else return true;	
}
-->
</script>
<%//-------接收lession_id参数------------
  	int lession_id=0;
	try{
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
    }catch(Exception e)
    {}
%>
<!----声明JavaBean，并查询出数据---->
<jsp:useBean id="lession_select" class="lessionman.lession_operation" scope="page"/>
<%ResultSet rs=lession_select.lession_select_one(lession_id);%>
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
          <td width="100%" colspan="3">修改课程信息：<br><hr></td>
        </tr>
        <form name="update_lession_form" action="lession_update2.jsp" method="post" onsubmit="return check_data()">
        <tr>
          <td width="25%" align="right">课程序号：<%=rs.getLong("lession_id")%>&nbsp;&nbsp;课程名：</td>
          <input type="hidden" value="<%=rs.getLong("lession_id")%>" name="lession_id">
          <td width="25%"><input type="text" name="lession_name" value="<%=rs.getString("lession_name")%>" maxlength="20"></td>
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