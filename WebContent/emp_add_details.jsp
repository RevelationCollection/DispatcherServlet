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
<form action="<%=addUrl%>" method="post" enctype="multipart/form-data">
	雇员编号：<input type="text" name="emp.empno" id="emp.empno" value="7369">${errors["emp.empno"]}<br>
	雇员姓名：<input type="text" name="emp.ename" id="emp.ename" value="SMITH">${errors["emp.ename"]}<br>
	基本工资：<input type="text" name="emp.sal" id="emp.sal" value="800.0">${errors["emp.sal"]}<br>
	雇佣日期：<input type="text" name="emp.hiredate" id="emp.hiredate" value="1981-10-10">${errors["emp.hiredate"]}<br>
	部门编号：<input type="text" name="emp.dept.deptno" id="emp.dept.deptno" value="10">${errors["emp.dept.deptno"]}<br>
	部门名称：<input type="text" name="emp.dept.dname" id="emp.dept.dname" value="开发部">${errors["emp.dept.dname"]}<br>
	公司名称：<input type="text" name="emp.dept.company.cname" id="emp.dept.company.cname" value="JBP">${errors["emp.dept.company.cname"]}<br>
	公司地址：<input type="text" name="emp.dept.company.address" id="emp.dept.company.address" value="北京-天安门">${errors["emp.dept.company.address"]}<br>
	个人照片：<input type="file" name="photo" id="photo"><br>
	<input type="submit" value="增加">
</form>  
</body> 
</html>