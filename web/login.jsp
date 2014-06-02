<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="util.commonTag"%>
<html>
<head>
<title>学生成绩管理系统</title>
</head>
<body bgcolor="#DCDADA">
<table border="0" width="100%" height="173">
  <tr>
    <td height="40">
     &nbsp; </td>
  </tr>
  <tr>
    <td width="100%" height="34">
      <p align="center"><font size="6"><b>学生成绩管理系统<font size="4">(Version 1.0)</font></b></font></td> 
  </tr>
  <tr>
    <td width="100%" height="21">
      <p align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
      ————供《JSP网络编程从基础到实践》读者学习使用，版权所有，违者必究！</td>
  </tr>
</table>
<table align="center" border="0">
   	<tr align="center"><td><font color="red"><html:errors/></font></td></tr>
    <tr align="center"><td>系统登录</td></tr>
    <form action="checkLogin.jsp" method="post" focus="sysuser_name">
    <tr><td>用户名：<input type="text" name="sysuser_name"/></td></tr>
    <tr><td>密&nbsp;&nbsp;码：<input type="password" name="sysuser_password"/></td></tr>
    <tr><td>角&nbsp;色：
    <select name="sysuser_role">
          	<option value="1">系统管理员</option>
          	<option value="2">教务管理员</option>
          	<option value="3">教师用户</option>
          	<option value="4">学生用户</option>
    </select>
    </td></tr>
    <tr><td>验证码：<input type="text" name="certCode"/>
    		<img src="makeCertPic.jsp">
    </td></tr>
    <tr align="left"><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" value="确定"/></td></tr>
    </form>
</table>

</body>
</html>