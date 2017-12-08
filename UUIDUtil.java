package com.liyuting.drug.commons.util;

import java.util.UUID;

/**
 * 生成uuid主键的工具类
 * @author Administrator
 *
 */
public class UUIDUtil {
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
