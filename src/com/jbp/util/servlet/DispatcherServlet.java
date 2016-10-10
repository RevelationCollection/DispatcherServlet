package com.jbp.util.servlet;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbp.util.bean.BeanValueUtil;
import com.jbp.util.split.SplitPageUtils;
import com.jbp.util.validator.Validation;
import com.jspsmart.upload.SmartUpload;

@SuppressWarnings("serial")
public abstract class DispatcherServlet extends HttpServlet {
	protected HttpServletRequest  request;
	protected HttpServletResponse response;
	private ResourceBundle pageResource;
	private ResourceBundle msgResource;
	private SmartUpload smart;
	/**
	 * 取得资源文件的对象
	 */
	@Override
	public void init() throws ServletException {
		pageResource = ResourceBundle.getBundle("Page", Locale.getDefault());
		msgResource = ResourceBundle.getBundle("Message", Locale.getDefault());
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		this.request = request;		
		this.response = response;
		String urlPage="error.jsp";		//定义错误跳转页面
		String status = this.getStatus();
		try {
			if(status!=null){
				String contentType = request.getContentType();
				try {
					if(contentType!=null&&contentType.contains("multipart/form-data")){
						this.smart = new SmartUpload();
						this.smart.initialize(super.getServletConfig(), request, response);
						this.smart.upload();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Map<String,String> error = Validation.validate(this);
				if(error.size()==0){
					this.parameterHandle();
					try {		//通过反射，调用add、edit、list、remove等方法
						Method statusMethod = this.getClass().getMethod(status);
						urlPage = statusMethod.invoke(this).toString();
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}else{
					request.setAttribute("errors", error);
					urlPage = this.getClass().getSimpleName()+"."+status+".error.page";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher(this.getPageValue(urlPage)).forward(request, response);	//页面跳转
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	/**
	 * 取得索要使用的方法（add,edit,remove,list）
	 * @return
	 */
	public String getStatus(){
		String uri = request.getRequestURI();	//取得网址 目标路径
		String status = uri.substring(uri.lastIndexOf("/")+1);		//取得状态码
		return status;
	}
	/**
	 * 对表单是否包装进行判断并处理，取出所需要的String类型的内容
	 * @return 返回表单所填的内容
	 */
	public String getStringParameter(String paramName){
		String contentType = this.request.getContentType();
		if(contentType!=null&&contentType.contains("multipart/form-data")){
				return this.smart.getRequest().getParameter(paramName);
		}else{
			return this.request.getParameter(paramName);
		}
	}
	/**
	 * 对表单是否包装进行判断并处理，取出所需要的Int类型的内容
	 */
	public int getIntParameter(String paramName){
		return Integer.parseInt(this.getStringParameter(paramName));
	}
	/**
	 * 对表单是否包装进行判断并处理，取出所需要的Double类型的内容
	 */
	public double getDoubleParameter(String paramName){
		return Double.parseDouble(this.getStringParameter(paramName));
	}
	/**
	 * 对表单是否包装进行判断并处理，取出所需要的Date类型的内容
	 */
	public Date getDateParameter(String paramName){
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(this.getStringParameter(paramName));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 对表单是否包装进行判断并处理，取出所需要的DateTime类型的内容
	 */
	public Date getDateTimeparameter(String paramName){
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.getStringParameter(paramName));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 处理Vo类对象的转换
	 */
	private void parameterHandle(){
		String contentType = this.request.getContentType();
		if(contentType!=null&&contentType.contains("multipart/form-data")){
			try {
				@SuppressWarnings("unchecked")
				Enumeration<String> enu = this.smart.getRequest().getParameterNames();
				while(enu.hasMoreElements()){
					String param = enu.nextElement();
					new BeanValueUtil(this,param,this.smart.getRequest().getParameter(param)).setObjectValue();
				}
			} catch (Exception e) {
			}
		}else{
			Enumeration<String> enu = request.getParameterNames();
			while(enu.hasMoreElements()){
				String param = enu.nextElement();
				new BeanValueUtil(this,param,this.request.getParameter(param)).setObjectValue();
			}
		}
	}
	/**
	 * 通过key取得对于资源文件的值
	 * @param pageKey跳转路径所对于的关键字
	 * @return 返回所需要的路径
	 */
	public String getPageValue(String pageKey){
		return this.pageResource.getString(pageKey);
	}
	/**
	 * 通过key取得对于资源文件的值
	 * @param pageKey跳转路径所对于的关键字
	 * @return 返回所需要的显示信息
	 */
	public String getMsgValue(String msgKey){
		return this.msgResource.getString(msgKey);
	}
	/**
	 * 通过request保存属性，在跳转页面后取出跳转路径和所需要显示的信息
	 * @param pageKey 跳转的路径关键字
	 * @param msgKey 所需要显示的关键字
	 */
	public void setUrlAndMsg(String pageKey,String msgKey){
		request.setAttribute("page", this.getPageValue(pageKey));
		if(this.getType()==null){
			request.setAttribute("msg", this.getMsgValue(msgKey));
		}else{
			request.setAttribute("msg", MessageFormat.format(this.getMsgValue(msgKey), this.getType()));
		}
	}
	/**
	 * 创建要保存的文件名称
	 */
	public String creatSingleName(){
		return UUID.randomUUID()+"."+this.smart.getFiles().getFile(0).getFileExt();
	}
	/**
	 * 保存上传的文件
	 * @param fileName 要保存的文件名称
	 * @return 保存成功返回true
	 */
	public boolean saveUploadFile(String fileName){
		try {
			//未指定保存路径则默认保存到根路径下
			if(this.getUploadDir()==null||"".equals(this.getUploadDir())){
				this.smart.getFiles().getFile(0).saveAs(super.getServletContext().getRealPath("/")+fileName);
			}else{
				String path = super.getServletContext().getRealPath("/")+this.getUploadDir()+fileName;
				File file = new File(path);
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				this.smart.getFiles().getFile(0).saveAs(path);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 判断是否有文件上传
	 * @return
	 */
	public boolean isUploadFile(){
		if(this.smart==null){
			return false;
		}
		try {
			if(this.smart.getFiles().getSize()>0)
				return true;
		} catch (IOException e) {
			return false;
		}
		return false;
	}
	/**
	 * 分页显示，保存路径，总数，页数，每页显示数，查询的字段，查询的关键字
	 * @param urlKey 通过路径key在资源文件Page中找到对应的路径
	 * @param allRecorders 取得总数，确定要显示的页数
	 * @param spu 其余四个内容
	 */
	public void setSplitPage(String urlKey,int allRecorders,SplitPageUtils spu){
		this.request.setAttribute("url", this.getPageValue(urlKey));
		this.request.setAttribute("allRecorders", allRecorders);
		this.request.setAttribute("currentPage", spu.getCurrentPage());
		this.request.setAttribute("lineSize",spu.getLineSize());
		this.request.setAttribute("column", spu.getColumn());
		this.request.setAttribute("keyWord", spu.getKeyWord());
		this.request.setAttribute("columnData", this.getDefaultColumn());
	}
	/**
	 * 取得上传文件路径
	 * @return
	 */
	protected abstract String getUploadDir();
	/**
	 * 设置要显示的关键字
	 * @return
	 */
	protected abstract String getType();
	/**
	 * 取得要显示的字段
	 * @return 返回对应的字段
	 */
	protected abstract String getDefaultColumn();
}
