/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import com.javenluo.ranger.common.persistence.DataEntity;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(FBIDP_SYS_USER_ROLE)的实体类
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysUserRole extends DataEntity<SysUserRole>{
	private static final long serialVersionUID = 1L;
	
	/** 角色编号（关联fbidp_sec_role表中的id字段） */
	private String roleId;  //角色编号（关联fbidp_sys_role表中的id字段）
	/** 人员编号（关联fbidp_sec_user表中的id字段） */
	private String userId;  //人员编号（关联fbidp_sys_user表中的id字段）
	
	private String code;
	private String name;
	private String orgCode;
	private String orgName;
	
}
