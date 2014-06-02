<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="studentman.student_operation,java.sql.ResultSet,core.core_operation"%>
<!--------接收输入参数---------->
<%//-------接收lession_id,class_id和all_count参数------------
  	int lession_id=0;
  	int class_id=0;
  	int all_count=0;
	try{
        lession_id=Integer.parseInt(request.getParameter("lession_id"));
        class_id=Integer.parseInt(request.getParameter("class_id"));
        all_count=Integer.parseInt(request.getParameter("all_count"));
    }catch(Exception e)
    {} 
    //-------接收录入的学生成绩----------
    student_operation all_class_student=new student_operation();
    ResultSet rs=all_class_student.student_select_part(class_id,null);
    rs.last();
    int rowCount=rs.getRow();
    rs.beforeFirst();
    String refName[]=new String[rowCount];
    String refValue[]=new String[rowCount];
    int i=0;
    while(rs.next()){
    	refName[i]=rs.getLong("student_id")+"";
    	refValue[i]=request.getParameter(rs.getLong("student_id")+"");
    	i++;
    }
    //-------保存数据--------------
    core_operation  coreOperation=new  core_operation();
    String returnString=coreOperation.saveStudent_core(refName,refValue,lession_id);
%>
<html>
<body bgcolor="#DCDADA">
<%=returnString%>
<a href="core_add.jsp?lession_id=<%=lession_id%>&class_id=<%=class_id%>">返回</a>
</body>
</html>