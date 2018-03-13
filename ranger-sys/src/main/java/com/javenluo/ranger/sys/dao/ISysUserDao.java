/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import java.util.List;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysBmfzr;
import com.javenluo.ranger.sys.entity.SysUser;

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
public interface ISysUserDao extends CrudDao<SysUser> {

	/**
	 * 根据登录名称查询用户
	 * 
	 * @param loginName
	 * @return
	 */
	public SysUser getByLoginName(SysUser user);
	
	/**
	 * 根据登录名称查询用户
	 * 
	 * @param loginName
	 * @return
	 */
	public List<SysUser> getByLoginId(SysUser user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * 
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(SysUser user);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	public int updateUserInfo(SysUser user);

	/**
	 * 插入用户角色关联数据
	 * 
	 * @param user
	 * @return
	 */
	public int insertUserRole(SysUser user);

	/**
	 * 删除用户角色关联数据
	 * 
	 * @param user
	 * @return
	 */
	public int deleteUserRole(SysUser user);

	/**
	 * 通过orgId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * 
	 * @param user
	 * @return
	 */
	public List<SysUser> findUserByOrgId(SysUser user);

	/**
	 * 通过orgCode获取用户列表。（任务查找机构人员中使用）
	 * 
	 * @param user
	 * @return
	 */
	public List<SysUser> findUserByOrgCode(SysUser user);

	/**
	 * 外部系统注册修改时根据longId 修改用户名
	 * 
	 * @param user
	 */
	public void updateName(SysUser user);

	/**
	 * 插入部门负责人
	 * 
	 * @param bmfzr
	 */
	public void insertBmfzr(SysBmfzr bmfzr);

	/**
	 * 查询部门负责人
	 * 
	 * @param bmfzr
	 * @return
	 */
	public SysBmfzr findBmfzr(SysBmfzr bmfzr);

	/**
	 * 删除部门负责人
	 * 
	 * @param loginId
	 */
	public void deleteBmfzr(String loginId);

	/**
	 *  校验用户重复
	 * @param user
	 * @return
	 */
	public Long userCount(SysUser user);

}
