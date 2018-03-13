/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysRole;

/**
 * <pre>
 * DAO接口
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @MyBatisDao
public interface ISysRoleDao extends CrudDao<SysRole>{

	/**
	 * 根据角色名获取角色
	 * @param role
	 * @return
	 */
	public SysRole getByName(SysRole role);
	
	/**
	 * 根据角色编码获取角色
	 * @param role
	 * @return
	 */
	public SysRole getByCode(SysRole role);
	
	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(SysRole role);
	
	/**
	 * 插入角色拥有菜单
	 * @param role
	 * @return
	 */
	public int insertRoleMenu(SysRole role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOrg(SysRole role);

	/**
	 * 插入角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int insertRoleOrg(SysRole role);
	 
}
