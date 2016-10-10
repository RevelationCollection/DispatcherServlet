package com.jbp.util;

import java.util.ResourceBundle;

public class ResourceUtils {
	private ResourceUtils(){}
	/**
	 * 取得所需要的资源文件的内容
	 * @param baseName 资源文件名称
	 * @param key 资源文件中的key
	 * @return 所需要的值
	 */
	public static String get(String baseName,String key){
		return ResourceBundle.getBundle(baseName).getString(key);
	}
}
