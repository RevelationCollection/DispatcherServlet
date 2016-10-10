<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path + "/";
	String addUrl = basePath + "emp/list" ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MVC设计分析</title>
</head>
<body>

<jsp:include page="/pages/split_page_search_plugin.jsp"/>
<div>
	${all}
</div>
<jsp:include page="/pages/split_page_bar_plugin.jsp"/>
</body>
</html>