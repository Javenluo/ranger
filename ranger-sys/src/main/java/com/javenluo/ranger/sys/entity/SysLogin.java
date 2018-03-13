/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import com.javenluo.ranger.common.persistence.DataEntity;
import com.javenluo.ranger.common.utils.excel.annotation.ExcelField;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(FBIDP_SYS_LOGIN)的实体类
 * </pre>
 * 
 * @author huangqi huangqi@foresee.com.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Data
public class SysLogin extends DataEntity<SysLogin> {

	private static final long serialVersionUID = 1L;

	/** 登录帐号 */
	private String loginId; // 登录帐号
	/** 登录密码 */
	private String passwd; // 登录密码

	public SysLogin(){
		
	}
	
	public SysLogin(String loginId) {
		this.setLoginId(loginId);
	}

	/**
	 * 获取登陆账号
	 * @return
	 */
	@ExcelField(title = "登录帐号", type = 1, align = 1, sort = 1)
	public String getLoginId() {
		return this.loginId;
	}

	/**
	 * 获取登录密码
	 * @return
	 */
	@ExcelField(title = "登录密码", type = 1, align = 1, sort = 2)
	public String getPasswd() {
		return this.passwd;
	}

}
