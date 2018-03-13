package com.javenluo.ranger.common.utils;

import lombok.Data;

/**
 * 返回值工具类
 * @author gulong
 * @version 2013-01-15
 */
@Data
public class ResponseData {
	public static final String SYSTEM_EXCEPTION = "Please contact the administrator.";
	public static final String MSG_ERROR_APPROVE_COMMENTS = "msg error approve comments";

	public void findMessage() {
		
	}

	// 返回信息
	private String msg;
	
	private boolean success = true;
	
	

}
