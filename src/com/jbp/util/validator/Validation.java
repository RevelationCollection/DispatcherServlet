package com.jbp.util.validator;

import java.util.HashMap;
import java.util.Map;

import com.jbp.util.ResourceUtils;
import com.jbp.util.servlet.DispatcherServlet;

public class Validation {
	private Validation(){}
	/**
	 * 对数据进行验证，如果验证失败，自动将错误信息加入到map集合中
	 * @param servlet
	 * @return
	 */
	public static Map<String,String> validate(DispatcherServlet servlet){
		Map<String,String> map = new HashMap<String,String>();
		try{
			String ruleKey = servlet.getClass().getSimpleName()+"."+servlet.getStatus()+".rules";
			String rule = ResourceUtils.get("Validations", ruleKey);
			if(rule!=null){
				String result[] = rule.split("\\|");
				for (int i = 0; i < result.length; i++) {
					String temp[] = result[i].split(":");
					String value = servlet.getStringParameter(temp[0]);
					switch(temp[1]){
						case "string":{
							if(!validateEmpty(value)){
								map.put(temp[0],servlet.getMsgValue("validation.string.msg"));
							}
							break;
						}
						case "int":{
							if(!validateInt(value)){
								map.put(temp[0], servlet.getMsgValue("validation.int.msg"));
							}
							break;
						}
						case "double":{
							if(!validateDouble(value)){
								map.put(temp[0], servlet.getMsgValue("validation.double.msg"));
							}
							break;
						}
						case "date":{
							if(!validateDate(value)){
								map.put(temp[0], servlet.getMsgValue("validation.date.msg"));
							}
							break;
						}
						case "datetime":{
							if(!validateDateTime(value)){
								map.put(temp[0],servlet.getMsgValue("validation.datetime.msg"));
							}
							break;
						}
					}
				}
			}
		}catch(Exception e){
		}
		return map;
	}
	/**
	 * 判断数据是否有时间组成
	 * @return 如果是返回true 如果不是返回false
	 */
	private static boolean validateDateTime(String str){
		if(validateEmpty(str)){
			return str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		}
		return false;
	}
	/**
	 * 判断数据是否有时间组成
	 * @return 如果是返回true 如果不是返回false
	 */
	private static boolean validateDate(String str){
		if(validateEmpty(str)){
			return str.matches("\\d{4}-\\d{2}-\\d{2}");
		}
		return false;
	}
	/**
	 * 判断数据是否有小数数字组成
	 * @return 如果是返回true 如果不是返回false
	 */
	private static boolean validateDouble(String str){
		if(validateEmpty(str)){
			return str.matches("\\d+(\\.\\d+)?");
		}
		return false;
	}
	/**
	 * 判断数据是否由数字组成
	 * @return 如果是返回true，如果不是返回false
	 */
	private static boolean validateInt(String str){
		if(validateEmpty(str)){
			return str.matches("\\d+");
		}
		return false;
	}
	/**
	 * 判断数据是否为空或者空字符串
	 * @return 如果是空返回false，如果不为空返回true
	 */
	public static boolean validateEmpty(String str){
		if(str==null||str.length()==0||"".equals(str))
			return false;
		return true;
	}
}
