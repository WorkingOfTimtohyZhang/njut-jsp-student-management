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
      <p align="left">您当前所在位置：学生成绩封存</p>
    </td>
  </tr>  
</table>
<!---------数据输入------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%" colspan="5">选择要封存的班级和课程：<br><hr></td>
        </tr>
        <form name="add_core_form" action="core_close.jsp" method="post" >
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
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
<jsp:useBean id="classlession_select" class="lessionman.classlession_operation" scope="page"/>
<%//-----用JavaBean查询出数据，并得到总记录条数------
	ResultSet rs=classlession_select.getCoreClassLessionRs(class_id,lession_id);
	rs.last();
	int rowCount=rs.getRow();
	rs.beforeFirst();
%> 
<jsp:useBean id="classlession_rsFenYe" class="util.rsFenYe" scope="page"/>
<jsp:setProperty name="classlession_rsFenYe" property="rs" value="<%=rs%>"/>
<!-------pageSize为每页记录条数--------->
<jsp:setProperty name="classlession_rsFenYe" property="pageSize" value="10"/>
  <tr>
    <td colspan="5" align="center">
<%//-----从请求参数中得到当前页码------
	String currentPage=request.getParameter("currentPage");
	try{
        classlession_rsFenYe.setCurrentPage(Integer.parseInt(currentPage));
    }catch(Exception e)
    {//如果参数不正确，设置当前页码为1
        classlession_rsFenYe.setCurrentPage(1);
    }
%>
    	所有有成绩的班级的课程(共<%=rowCount%>个)&nbsp;&nbsp;&nbsp;&nbsp;
    	<%String refName[]={"lession_id","class_id"};
		  String refValue[]={lession_id+"",class_id+""};%>
    	<%=classlession_rsFenYe.earn_fenyi_string("core_close.jsp",refName,refValue)%>
    </td>
  </tr>
  <tr>
    <td width="20%" align="center">班级名称</td>
    <td width="20%" align="center">课程名称</td>
    <td width="20%" align="center">目前封存状态</td>
    <td width="20%" align="center">封存？</td>
    <td width="20%" align="center">解封？</td>
  </tr>
  <%for(int i=0;i<classlession_rsFenYe.getPageSize()&&rs.next();i++){%>
  <tr>
    <td align="center"><%=rs.getString("class_name")%></td>
    <td align="center"><%=rs.getString("lession_name")%></td>
    <td align="center">
    <%//-----------输出封存状态---------------
      classlession_operation classlession=new classlession_operation();
      out.print(classlession.getClassLessionCloseStatus(rs.getInt("class_id"),rs.getInt("lession_id")));
      %>
    </td>
    <td align="center">
    <a href='core_close_save.jsp?class_id=<%=rs.getInt("class_id")%>&lession_id=<%=rs.getInt("lession_id")%>'>封存</a></td>
    <td align="center">
    <a href='core_open_save.jsp?class_id=<%=rs.getInt("class_id")%>&lession_id=<%=rs.getInt("lession_id")%>'>解封</a></td>
    </td>
  </tr>
  <%
  	}
  %>
</table><br>
<!----------操作提示信息--------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%">注意：1.某班某科目的成绩封存后，教师可录入还没有录入的学生的成绩，但不能更改此班此科目的成绩；<br>
          &nbsp;&nbsp;&nbsp;&nbsp;
          2.如果封存状态为部分封存或未知，请再作一次封存或解封操作。<br>
         </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>