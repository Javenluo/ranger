/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javaluo.ranger.rest.entity;

import com.foresee.fbidp.common.persistence.DataEntity;

import lombok.Data;

/**
 * token实体类
 * @author gulong 
 * @version 2017-10-16
 */
 @Data
public class SysToken extends DataEntity<SysToken>{

	private static final long serialVersionUID = 1L;
	
	/** 令牌KEY */
	private String key;
	/** 令牌值 */
	private String token;
	/** 到期时间 */
	private java.util.Date expireTime;

}
