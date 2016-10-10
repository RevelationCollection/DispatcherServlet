<%@ page pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	// request.setCharacterEncoding("UTF-8") ;
%>
<%
	request.setCharacterEncoding("UTF-8") ;
%>
<%
	String url =basePath+ request.getAttribute("url") ;
	Integer allRecorders = (Integer)request.getAttribute("allRecorders") ;
	String columnData = (String)request.getAttribute("columnData") ;
	String column = (String)request.getAttribute("column") ;
	String keyWord = (String)request.getAttribute("keyWord") ;
	out.print(keyWord);
%>
<div id="searchDiv">
	<form action="<%=url%>" method="post">
<%		// 现在columnData变量有内容
		if (!(columnData == null || "".equals(columnData))) {
			String result [] = columnData.split("\\|") ;	// 拆分
%>
			<select id="col" name="col">
<%
				for (int x = 0 ; x < result.length ; x ++) {
					String temp [] = result[x].split(":") ;
%>
					<option value="<%=temp[1]%>" <%=temp[1].equals(column) ? "selected" : ""%>><%=temp[0]%></option>
<%
				}
%>
			</select>
<%
		}
%>
		<input type="text" name="kw" id="kw" placeholder="请输入检索关键字" value="<%= keyWord==null?"":keyWord%>">
		<input type="submit" value="检索">
		<div>本次查询一共返回有“<%=allRecorders%>”行记录！</div>
	</form>
</div>