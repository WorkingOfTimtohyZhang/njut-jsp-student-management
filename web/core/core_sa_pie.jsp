<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="util.commonTag,java.sql.ResultSet,lessionman.classlession_operation"%>
<html>
<!--------接收输入参数---------->
<%//-------接收lession_id和class_id参数------------
  	int lession_id=0;
  	int class_id=0;
	try{
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
        class_id=Integer.parseInt(request.getParameter("class_id"));
    }catch(Exception e)
    {}   
%>
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<table border="0" width="100%">
  <tr>
    <td width="100%">
      <p align="left">您当前所在位置：学生成绩分析</p>
    </td>
  </tr>  
</table>
<!---------数据输入------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%" colspan="5">选择要分析的班级和课程：<br><hr></td>
        </tr>
        <form action="core_sa_pie.jsp" method="post" >
        <tr>
          <td width="25%" align="right">班级：</td>
          <td width="10%">
          <%//-----生成班级下拉框------
          	commonTag classtag=new commonTag();
          	out.println(classtag.getClassTag(class_id));
          %>
          </td>
          <td width="15%" align="right">课程名：</td>
          <td width="20%">
          <%//-----生成课程下拉框------
          	out.println(classtag.getLessionTag(lession_id));
          %>
          </td>
          <td width="30%"><input type="submit" value="提交"></td>
        </tr>
        </form>
      </table>
    </td>
  </tr>
</table><br>
<!---------数据输出------------->
<table border="0" align="center">
	<tr align="center"><td align="center">
		<img src="../getCorePieChart?class_id=<%=class_id%>&lession_id=<%=lession_id%>">
	</td></tr>
</table>
</body>
</html>






