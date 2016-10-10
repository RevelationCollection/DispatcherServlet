<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path + "/";
	String addUrl = basePath + "emp/add" ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MVC设计分析</title>
</head>
<body>
<form action="<%=addUrl%>" method="post">
	雇员编号：<input type="text" name="emp.empno" id="emp.empno" value="7369"><br>
	雇员姓名：<input type="text" name="emp.ename" id="emp.ename" value="SMITH"><br>
	基本工资：<input type="text" name="emp.sal" id="emp.sal" value="800.0"><br>
	雇佣日期：<input type="text" name="emp.hiredate" id="emp.hiredate" value="1981-10-10"><br>
	<input type="submit" value="增加">
</form> 
</body>
</html>