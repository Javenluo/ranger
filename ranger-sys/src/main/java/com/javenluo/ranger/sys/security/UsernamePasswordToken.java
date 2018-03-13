/**
 * Copyright &copy; 2012-2016 <a href="www.foresee.com">Forsee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.security;

import lombok.Data;

/**
 * 用户和密码（包含验证码）令牌类
 * @author gulong
 * @version 2013-5-19
 */
@Data
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String captcha;
	private boolean mobileLogin;
	
	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String userId,String username, char[] password,
			boolean rememberMe, String host, String captcha, boolean mobileLogin) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
		this.userId = userId;
	}

	/**
	 * 获取验证码
	 * @return
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * 验证码赋值
	 * @param captcha
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	/**
	 * 是否手机号码登陆
	 * @return
	 */
	public boolean isMobileLogin() {
		return mobileLogin;
	}
	
}