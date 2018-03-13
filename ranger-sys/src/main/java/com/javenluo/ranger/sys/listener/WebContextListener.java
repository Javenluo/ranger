package com.javenluo.ranger.sys.listener;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

import com.foresee.fbidp.common.config.Global;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	static Log log = LogFactory.getLog(WebContextListener.class);
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 "+Global.getConfig("productName")+" - 方欣科技有限公司-数据利用部\r\n");
		sb.append("\r\n======================================================================\r\n");
		log.info(sb.toString());
		return true;
	}
}
