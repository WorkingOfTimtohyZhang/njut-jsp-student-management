<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.sql.ResultSet,util.commonTag"%>
<html>
<!--------接收输入参数---------->
<%//-------接收lession_id和teacher_id参数------------
  	int lession_id=0;
  	int teacher_id=0;
	try{
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
        teacher_id=Integer.parseInt(request.getParameter("teacher_id"));
    }catch(Exception e)
    {}
%>
<body bgcolor="#DCDADA">
<!---------导航菜单------------->
<table border="0" width="100%">
  <tr>
    <td width="100%">
      <p align="left">您当前所在位置：教师授课信息管理-->查询和维护教师授课信息</p>
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
          <td width="100%" colspan="5">输入查询条件：<br><hr></td>
        </tr>
        <form name="add_teachlession_form" action="teachlessionman.jsp" method="post">
        <tr>
          <td width="33%" align="right">授课教师：</td>
          <td width="13%">
          <%//-----生成教师下拉框------
          	commonTag classtag=new commonTag();
          	out.println(classtag.getTeacherTag(teacher_id));
          %>
          </td>
          <td width="7%" align="right">课程：</td>
          <td width="17%">
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
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
<jsp:useBean id="teachlession_select" class="teachlessionman.teachlession_operation" scope="page"/>
<%//-----用JavaBean查询出数据，并得到总记录条数------
	ResultSet rs=teachlession_select.teachlession_select_part(lession_id,teacher_id);
	rs.last();
	int rowCount=rs.getRow();
	rs.beforeFirst();
%> 
<jsp:useBean id="teachlession_rsFenYe" class="util.rsFenYe" scope="page"/>
<jsp:setProperty name="teachlession_rsFenYe" property="rs" value="<%=rs%>"/>
<!-------pageSize为每页记录条数--------->
<jsp:setProperty name="teachlession_rsFenYe" property="pageSize" value="10"/>
  <tr>
    <td colspan="5" align="center">
<%//-----从请求参数中得到当前页码------
	String currentPage=request.getParameter("currentPage");
	try{
        teachlession_rsFenYe.setCurrentPage(Integer.parseInt(currentPage));
    }catch(Exception e)
    {//如果参数不正确，设置当前页码为1
        teachlession_rsFenYe.setCurrentPage(1);
    }
%>
    	所有教师授课信息(共<%=rowCount%>条)&nbsp;&nbsp;&nbsp;&nbsp; 
<%
	String refName[]={"lession_id","teacher_id"};
	String refValue[]={lession_id+"",teacher_id+""};
	out.print(teachlession_rsFenYe.earn_fenyi_string("teachlessionman.jsp",refName,refValue));
%>
    </td>
  </tr>
  <tr>
    <td width="20%" align="center">教师授课信息序号</td>
    <td width="15%" align="center">教师姓名</td>
    <td width="25%" align="center">所教课程</td>
    <td width="20%" align="center">修改？</td>
    <td width="20%" align="center">删除？</td>
  </tr>
  <%for(int i=0;i<teachlession_rsFenYe.getPageSize()&&rs.next();i++){%>
  <tr>
    <td align="center" width="132"><%=rs.getLong("teachlession_id")%></td>
    <td align="center" width="163"><%=rs.getString("teacher_name")%></td>
    <td align="center" width="234"><%=rs.getString("lession_name")%></td>
    <td align="center" width="176">
    <a href='teachlession_update1.jsp?teachlession_id=<%=rs.getLong("teachlession_id")%>&lession_id=<%=rs.getLong("lession_id")%>&teacher_id=<%=rs.getLong("teacher_id")%>'>修改</a>
    </td>
    <td align="center" width="176">
    <a href="teachlession_delete.jsp?teachlession_id=<%=rs.getLong("teachlession_id")%>" onclick="return delete_confirm()">×</a>
    </td>
  </tr>
  <%
  	}
  %>
</table><br>
</body>
</html>