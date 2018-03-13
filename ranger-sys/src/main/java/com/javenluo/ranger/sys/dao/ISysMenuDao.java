/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import java.util.List;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysMenu;

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
public interface ISysMenuDao extends CrudDao<SysMenu>{

	 /**
	  * 获取用户所拥有的角色菜单
	  * @param menu SysMenu
	  * @return List<SysMenu>
	  */
	public List<SysMenu> findByUserId(SysMenu menu);
	
	/**
	 * 根据父id获取角色菜单
	 * @param menu
	 * @return
	 */
	public List<SysMenu> findMenuByParentId(SysMenu menu);
	
	/**
	 * 根据父id查找子节点
	 * @param menu
	 * @return
	 */
	public List<SysMenu> findByParentIdsLike(SysMenu menu);
	
	/**
	 * 更新父id
	 * @param menu
	 * @return
	 */
	public int updateParentIds(SysMenu menu);
	
	/**
	 * 更新排序
	 * @param menu
	 * @return
	 */
	public int updateSort(SysMenu menu);
	
	/**
	 * 查找角色集合
	 * @param menu 
	 * @return
	 */
	public List<SysMenu> findRoleList(SysMenu menu);
	
	/**
	 * 根据菜单id查询帮助说明
	 * @param menu
	 * @return
	 */
	public String findBzsmById(SysMenu menu);

	/**
	 * 根据菜单id修改帮助说明
	 * @param menu
	 * @return
	 */
	public int updateBzsmById(SysMenu menu);
	
	/**
	 * 菜单名称重复验证
	 * @param menu
	 * @return
	 */
	public Long menuCount(SysMenu menu);
}
