<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="user.user_operation,
                 java.sql.ResultSet,
                 java.sql.SQLException" %>
<%
    //-------接收输入参数------------
    int sysuser_role = 0;
    ResultSet rs = null;
    try {
        sysuser_role = Integer.parseInt(request.getParameter("sysuser_role"));
    } catch (Exception e) {
        out.println(e.getStackTrace());
    }
    String sysuser_password = request.getParameter("sysuser_password");
    String sysuser_name = request.getParameter("sysuser_name");
    String certCode = request.getParameter("certCode");

    //------查询数据库------
    user_operation uop = new user_operation();
    rs = uop.getUserOne(sysuser_name, sysuser_password, sysuser_role);
    int rowCount = 0;
    try {
        rs.next();
        rowCount = 1;
    } catch (SQLException e) {
    }
    if (rowCount != 0&&certCode.equals((String)session.getAttribute("certCode"))) {//
        //------通过检查------
        session.setAttribute("sysuser_id", rs.getString("sysuser_id"));
        response.sendRedirect("\\index.jsp");
    } else {
        response.sendRedirect("\\login.jsp");
    }
%>
