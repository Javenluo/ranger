/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import java.util.List;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysRole;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.entity.SysUserRole;

/**
 * <pre>
 * DAO接口
 * </pre>
 * 
 * @author gulong
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@MyBatisDao
public interface ISysUserRoleDao extends CrudDao<SysUserRole> {

	/**
	 * 查询出用户未被授权的的角色
	 * @param entity
	 * @return Page<SysRole>
	 */
	public List<SysRole> queryNotGrantRoleById(SysUserRole entity);

	/**
	 * 查询出用户已被授权的的角色
	 * @param entity
	 * @return Page<SysRole>
	 */
	public List<SysRole> queryGrantRoleById(SysUserRole entity);

	/**
	 * 根据用户ID物理删除
	 * @param entity
	 */
	public void deleteByUserId(SysUserRole entity);

	/**
	 * 查询角色下的人员信息
	 * @param entity
	 * @return
	 */
	public List<SysUser> querySysUserBySysUserRole(SysUserRole entity);
	
	/**
	 * 查询角色下的人员数量
	 * @param entity
	 * @return
	 */
	public long querySysUserBySysUserRoleCount(SysUserRole entity);

	/**
	 * 角色已选择用户
	 * @param entity
	 * @return
	 */
	public List<SysUser> roleYxUser(SysUser entity);

	/**
	 * 角色未选择用户
	 * @param entity
	 * @return
	 */
	public List<SysUser> roleWxUser(SysUser entity);

}
