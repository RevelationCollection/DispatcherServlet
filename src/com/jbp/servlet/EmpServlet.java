package com.jbp.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.jbp.util.servlet.DispatcherServlet;
import com.jbp.util.split.SplitPageUtils;
import com.jbp.vo.Emp;

@SuppressWarnings("serial")
@WebServlet(urlPatterns="/emp/*")
public class EmpServlet extends DispatcherServlet{
	private Emp emp = new Emp();
	public Emp getEmp(){
		return emp;
	}
	public String add(){	
		if(super.isUploadFile()){
			String fileName = super.creatSingleName();
			super.saveUploadFile(fileName);
		}
		super.setUrlAndMsg("emp.add.page", "vo.add.success.msg");
		System.out.println("增加成功"+this.emp);
		return "forward.page";
	}
	public String list(){
		String urlKey = "EmpServlet.list.page";
		Integer allRecorders = 50;
		SplitPageUtils spu = new SplitPageUtils(super.request);
		int lineSize = spu.getLineSize();
		int currentPage = spu.getCurrentPage();
		List<Emp> all = new ArrayList<Emp>();
		for(int x=(currentPage-1)*lineSize;x<(currentPage*lineSize);x++){
			Emp emp = new Emp();
			emp.setEname("雇员姓名 - " + spu.getColumn() + " - " + spu.getKeyWord() + " - " + x);
			emp.setEmpno(1000+x);
			all.add(emp);
		}
		super.request.setAttribute("all", all);
		super.setSplitPage(urlKey, allRecorders, spu);
		System.out.println("输出列表信息");
		return "emp.list.page";
	}
	@Override
	protected String getType() {
		return "雇员";
	}
	@Override
	public String getUploadDir() {
		return "/upload/emp/";
	}
	@Override
	protected String getDefaultColumn() {
		return "雇员姓名:ename|雇员职位:job|联系电话:phone|邮箱:email";
	}

	
}
