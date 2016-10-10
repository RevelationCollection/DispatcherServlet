package com.jbp.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.jbp.util.StringUtils;

public class BeanValueUtil {
	private Object servletObject;
	private String attributeName;
	private String attributeValue;
	public BeanValueUtil(Object servletObject,String attributeName,String attributeValue){
		this.servletObject = servletObject;
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}
	/**
	 * 通过反射为Vo类设置属性
	 */
	public void setObjectValue(){
		Object currentObject = null;
		//查询所取得表的名称，并进行拆分（默认格式  vo类名称.参数名称）
		if(this.attributeName.contains(".")){
			String result[] = attributeName.split("\\.");
			try {	
				//取得Vo类实例化对象
				Method getMethod = servletObject.getClass().getMethod("get"+StringUtils.fistCaps(result[0]));
				currentObject = getMethod.invoke(this.servletObject);
				//判断取得的表的名称长度是否为2，如果为2直接调用set方法
				if(result.length==2){				
					Field field = currentObject.getClass().getDeclaredField(result[1]);
					Method setMethod = currentObject.getClass().getMethod("set"+StringUtils.fistCaps(result[1]),field.getType());
					setMethod.invoke(currentObject, this.convertValue(field.getType().getSimpleName()));
				}else{
					//长度大于2，循环调用get方法，循环的次数要-1，最后一个调用set方法
					for(int x=1;x<result.length-1;x++){
						Field field = currentObject.getClass().getDeclaredField(result[x]);		
						Method getSubMethod = currentObject.getClass().getMethod("get"+StringUtils.fistCaps(result[x]));
						//判断get方法掉用户取得的对象是否为空，如果为空则为其实例化，如果不为空则保存后为下一次调用方法做准备
						if(getSubMethod.invoke(currentObject)==null){
							currentObject = this.objectNewInstance(currentObject, result[x], field);
						}else{
							currentObject = getSubMethod.invoke(currentObject);
						}
					}
					//循环结束，调用set方法
					Field attField = currentObject.getClass().getDeclaredField(result[result.length-1]);
					Method setMethod = currentObject.getClass().getMethod("set"+StringUtils.fistCaps(result[result.length-1]), attField.getType());
					setMethod.invoke(currentObject, this.convertValue(attField.getType().getSimpleName()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	/**
	 * 为Vo（emp.dept）类对象实例化
	 * @param currentObject 调用方法的实例化对象 
	 * @param methodName 方法名
	 * @param field 参数的类型
	 * @return 新的实例化对象
	 * @throws Exception
	 */
	private Object objectNewInstance(Object currentObject,String methodName,Field field) throws Exception{
		try {
			Object newObject = field.getType().newInstance();
			Method setMethod = currentObject.getClass().getMethod("set"+StringUtils.fistCaps(methodName), field.getType());
			setMethod.invoke(currentObject, newObject);
			return newObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		
	}
	/**
	 * 根据参数指定的类型，将内容进行转型
	 * @param type 参数类型
	 * @return 
	 */
	public Object convertValue(String type){
		if("int".equals(type)||"Integer".equals(type)){
			return Integer.parseInt(this.attributeValue);
		}else if("String".equals(type)){
			return this.attributeValue;
		}else if("double".equalsIgnoreCase(type)){
			return Double.parseDouble(this.attributeValue);
		}else if("Date".equals(type)){
			if(this.attributeValue.matches("\\d{4}-\\d{2}-\\d{2}")){
				try {
					return new SimpleDateFormat("yyyy-MM-dd").parse(attributeValue);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if(this.attributeValue.matches("\\d-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")){
				try {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(attributeValue);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
