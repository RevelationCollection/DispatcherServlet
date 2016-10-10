package com.jbp.util.split;

import javax.servlet.http.HttpServletRequest;

public class SplitPageUtils {
	private HttpServletRequest request;
	public SplitPageUtils(HttpServletRequest request){
		this.request = request;
	}
	/**
	 * 取得页数
	 */
	public Integer getCurrentPage(){
		Integer currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("cp"));
		} catch (Exception e) {
		}
		return currentPage;
	}
	/**
	 * 取得每页显示数
	 */
	public Integer getLineSize(){
		Integer lineSize = 10;
		try {
			lineSize = Integer.parseInt(request.getParameter("ls"));
		} catch (Exception e) {
		}
		return lineSize;
	}
	/**
	 * 取得字段
	 */
	public String getColumn(){
		return request.getParameter("col");
	}
	/**
	 * 取得关键字
	 */
	public String getKeyWord(){
		return request.getParameter("kw");
	}
}
