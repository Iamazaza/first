package com.liyuting.drug.commons.util;
/**
 * service工厂类
 * @author Administrator
 *
 */
public class ServiceFactory {
	/**
	 * 获取service的代理对象
	 * @param service
	 * @return
	 */
	public static Object getServiceProxy(Object service){
		return new TransactionInvocationHandler(service).getProxy();
	}
}
