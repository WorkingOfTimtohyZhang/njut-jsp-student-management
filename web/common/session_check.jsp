<%
	if(session.getAttribute("sysuser_id")==null||session.getAttribute("sysuser_id").toString().length()==0)
		response.sendRedirect(request.getContextPath()+"/login.jsp");		
%>