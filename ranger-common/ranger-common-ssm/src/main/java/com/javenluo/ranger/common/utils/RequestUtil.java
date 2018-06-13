package com.javenluo.ranger.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Request工具类
 * @author gulong
 * @version 2013-01-15
 */
public class RequestUtil {
	public static final String X_REQUESTED_WIDTH = "X-Requested-With";
	public static final String XML_HTTP_REQUEST = "XMLHttpRequest";

	private RequestUtil() {

	}

	/**
	 * 判断是否ajax请求. 可以看到Ajax 请求多了个 x-requested-with ，可以利用它，
	 * request.getHeader("x-requested-with"); 为 null，则为传统同步请求，为
	 * XMLHttpRequest，则为Ajax 异步请求。
	 * @param request HttpServletRequest 
	 * @return boolean 是否ajax请求.
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String xr = request.getHeader(X_REQUESTED_WIDTH);
		return (xr != null && XML_HTTP_REQUEST.equalsIgnoreCase(xr));
	}

	/**
	 * 获取Cookie value值
	 * @param request 请求对象
	 * @param name Cookie名称
	 * @return String
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
