<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="util.commonTag,java.sql.ResultSet,lessionman.classlession_operation"%>
<html>
<!------系统交互javascript----->
<script language="JavaScript">
<!--
function check_data() {
//按提交按钮时，检查输入数据
   if(add_core_form.class_id.value==0){
   		alert("请选择班级！");
   		return false;
   }
   if(add_core_form.lession_id.value==0){
   		alert("请选择课程！");
   		return false;
   }
}
-->
</script>
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
      <p align="left">您当前所在位置：学生成绩录入-->查询和录入学生成绩信息</p>
    </td>
  </tr>  
</table>
<!---------数据输入------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%" colspan="5">查询和录入学生成绩信息：<br><hr></td>
        </tr>
        <form name="add_core_form" action="core_add.jsp" method="post" onsubmit="return check_data()">
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
<%if(class_id!=0&&lession_id!=0){%>
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
<jsp:useBean id="student_select" class="studentman.student_operation" scope="page"/>
<%//-----用JavaBean查询出数据，并得到总记录条数------
	classlession_operation classLession=new classlession_operation();
	if(classLession.getClassLessionCloseStatus(class_id,lession_id).equals("已封存")){
%>
  <tr>
    <td colspan="3" align="center">
    	此班级的此门课程成绩已被封存！
    </td>
  </tr>
<%	}else{
		ResultSet rs=student_select.student_select_part(class_id,null);
		rs.last();
		int rowCount=rs.getRow();
		rs.beforeFirst();
%> 
<form name="student_core" action="core_save.jsp" method="post" onsubmit="return check_core_data()">
  <tr>
    <td colspan="3" align="center">
    	学生课程成绩(共<%=rowCount%>条) 
    </td>
  </tr>
  <tr>
    <td colspan="3" align="right">
    	<input type="submit" value="保存成绩">
    </td>
  </tr>
  <tr>
    <td width="20%" align="center">学生序号</td>
    <td width="40%" align="center">学生姓名</td>
    <td width="40%" align="center">课程成绩</td>
  </tr>
  <input type="hidden" value="<%=lession_id%>" name="lession_id">
  <input type="hidden" value="<%=class_id%>" name="class_id">
  <jsp:useBean id="core_operation" class="core.core_operation" scope="page"/>
  <%
  int i=0;
  while(rs.next()){%>
  <tr>
    <td align="center" ><%=rs.getLong("student_id")%></td>
    <td align="center" ><%=rs.getString("student_name")%></td>
    <td align="center" >
    <input type="text" maxlength="10" name="<%=rs.getLong("student_id")%>" value="<%=core_operation.getStudent_lession_core(rs.getLong("student_id"),lession_id)%>">
    </td>
  </tr>
  <%i++;
  }%>
  <input type="hidden" name="all_Count" value="<%=i%>">
 </form>
 <%}%>
</table><br>
<%}%>
<!----------操作提示信息--------------->
<table border="1" width="100%" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#808080" bordercolordark="#808080">
  <tr>
    <td width="100%">
      <table border="0" width="100%">
        <tr>
          <td width="100%">注意：单击保存按钮会保存当前屏幕上的所有学生的此课程的成绩。</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>