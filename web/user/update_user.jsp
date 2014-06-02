<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet,util.commonTag,
				 user.user_operation"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查数据是否为空
   if(update_user_form.sysuser_password.value.length==0||update_user_form.sysuser_repassword.value.length==0){
  		alert("请输入密码！");
  		return false;
	}
	if(update_user_form.sysuser_password.value!=update_user_form.sysuser_repassword.value){
  		alert("两次输入的密码不同！");
  		return false;
	}
}
-->
</script>
<%//-------接收参数------------
  	int sysuser_id=0;
  	int sysuser_role=0;
  	ResultSet rs=null;
	try{
        sysuser_id=Integer.parseInt(request.getParameter("sysuser_id"));
        sysuser_role=Integer.parseInt(request.getParameter("sysuser_role"));
    }catch(Exception e){}
    String sysuser_password=request.getParameter("sysuser_password");
    String sysuser_name=request.getParameter("sysuser_name");
    //out.print(sysuser_id+sysuser_role+sysuser_password+"*"+sysuser_name); 
    if(sysuser_password!=null&&sysuser_name!=null&&sysuser_role!=0){
    	//------修改用户信息------
    	//out.print("sss");
    	user_operation uop=new user_operation();
    	uop.update_sysuser(sysuser_id,sysuser_name,sysuser_password,sysuser_role);
%>
<body bgcolor="#DCDADA">
	修改用户信息成功!<a href="auto_gen_user.jsp">返回</a>
</body>
</html>
<%
	}else{
	//------查询用户信息------
		user_operation uop=new user_operation();
	 	rs=uop.getUserByPrimKey(sysuser_id);
	 	rs.next();
%>
<!----声明JavaBean，并查询出数据---->
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<jsp:include page="navigator.txt"/>
<!---------数据输入------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="103%">
        <tr>
          <td width="100%" colspan="4">修改用户信息：<br><hr></td>
        </tr>
        <form name="update_user_form" method="post" onsubmit="return check_data()">
        <tr>
          <td align="20%">&nbsp;</td>
          <td align="right">用户ID：</td>
          <td align="left">
          <input type="hidden" name="sysuser_id" value="<%=rs.getString("sysuser_id")%>">
          <%=rs.getString("sysuser_id")%>
          </td>
         <tr>
        <tr>
          <td align="20%">&nbsp;</td>
          <td align="right">用户名：</td>
          <td align="left"><input type="text" name="sysuser_name" value="<%=rs.getString("sysuser_name")%>"></td>
         <tr>
         <tr>
          <td align="20%">&nbsp;</td>
          <td align="right">密码：</td>
          <td align="left">
          <input type="password" name="sysuser_password" value="<%=rs.getString("sysuser_password")%>"></td>
         <tr>
         <tr>
          <td align="20%">&nbsp;</td>
          <td align="right">再输入一次密码：</td>
          <td align="left"><input type="password" name="sysuser_repassword" value="<%=rs.getString("sysuser_password")%>"></td>
         <tr>
         <tr>
          <td align="20%">&nbsp;</td>
          <td align="right">用户角色：</td>
          <td align="left">
          <select name="sysuser_role">
          	<option value="1" <%if(rs.getInt("sysuser_role")==1) out.print("selected");%>>系统管理员</option>
          	<option value="2" <%if(rs.getInt("sysuser_role")==2) out.print("selected");%>>教务管理员</option>
          	<option value="3" <%if(rs.getInt("sysuser_role")==3) out.print("selected");%>>教师用户</option>
          </select>
          </td>
         <tr>
         <tr>
          <td width="100%" colspan="4" align="center">
          <input type="submit" value="提交">
          </td>
         </tr>
        </form>
      </table>
    </td>
  </tr>
</table>
<br>
</body>
</html>
<%}%>