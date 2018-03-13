/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import com.javenluo.ranger.common.persistence.DataEntity;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(fbidp_sys_role_menu)的实体类
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysRoleMenu extends DataEntity<SysRoleMenu>{
	private static final long serialVersionUID = 1L;
	
	/** 角色ID */
	private String roleId;  //角色ID
	/** 菜单ID */
	private String menuId;  //菜单ID
	
	private String menuIds;

}
