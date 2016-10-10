package com.jbp.util;

public class StringUtils {
	private StringUtils(){}
	/**
	 * 将字符串首字母进行大写转换
	 */
	public static String fistCaps(String str){
		if(str==null||str.length()==0)
			return null;
		if(str.length()==1)
			return str.toUpperCase();
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}
}
