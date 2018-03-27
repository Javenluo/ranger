/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javenluo.ranger.common.persistence.Page;
import com.javenluo.ranger.common.service.CrudService;
import com.javenluo.ranger.common.utils.CacheUtils;
import com.javenluo.ranger.sys.dao.ISysRoleDao;
import com.javenluo.ranger.sys.dao.ISysRoleMenuDao;
import com.javenluo.ranger.sys.dao.ISysUserDao;
import com.javenluo.ranger.sys.dao.ISysUserRoleDao;
import com.javenluo.ranger.sys.entity.SysRole;
import com.javenluo.ranger.sys.entity.SysRoleMenu;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.entity.SysUserRole;
import com.javenluo.ranger.sys.utils.UserUtils;

/**
 * <pre>
 * Service服务类
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class SysRoleService extends CrudService<ISysRoleDao,SysRole> {
	
	@Autowired
	private ISysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private ISysUserRoleDao sysUserRoleDao;
	@Autowired
	private ISysUserDao sysUserDao;
	
	public SysRole get(String id) {
		return super.get(id);
	}
	
	public List<SysRole> findList(SysRole entity) {
		return super.findList(entity);
	}
	
	public Page<SysRole> findPage(SysRole entity) {
		return super.findPage(entity);
	}
	
	@Transactional(readOnly = false)
	public void save(SysRole entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void insert(SysRole entity) {
		entity.preInsert();
		dao.insert(entity);
	}
	
	@Transactional(readOnly = false)
	public void update(SysRole entity) {
		entity.preUpdate();
		dao.update(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysRole entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.setId(id);
				super.delete(entity);
			}
			
		}
		
	}
	
	/**
	 * 保存角色关联菜单
	 * @param sysRoleMenu
	 */
	@Transactional(readOnly = false)
	public void saveRoleMenu(SysRoleMenu sysRoleMenu){
		//1:先删除角色已关联的菜单关联
		sysRoleMenuDao.deleteByRoleId(sysRoleMenu.getRoleId());
		
		//2:插入新的关联关系
		if(StringUtils.isNotEmpty(sysRoleMenu.getMenuIds())){
			String[] menuIds = sysRoleMenu.getMenuIds().split("&amp;");
			for (String menuId : menuIds) {
				SysRoleMenu entity = new SysRoleMenu();
				entity.preInsert();
				entity.setRoleId(sysRoleMenu.getRoleId());
				entity.setMenuId(menuId);
				sysRoleMenuDao.insert(entity);
			}
		}
	}
	
	/**
	 * 查询出用户未被授权的的角色
	 * @param entity SysUserRole
	 * @return Page<SysRole>
	 */
	public Page<SysRole> queryNotGrantRoleById(SysUserRole entity){
		Page<SysRole> page = new Page<SysRole>();
		
		PageHelper.startPage(entity.getPage().intValue(), entity.getRows().intValue());
		List<SysRole> list = sysUserRoleDao.queryNotGrantRoleById(entity);
		page.setRows(list);
		
		PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(list);
        page.setTotal(pageInfo.getTotal());
        
        page.setEntity(new SysRole());
		
		return page;
	}
	
	/**
	 * 查询出用户未被授权的的角色
	 * @param entity SysUserRole
	 * @return List<SysRole>
	 */
	public List<SysRole> queryGrantRoleById(SysUserRole entity){
		return sysUserRoleDao.queryGrantRoleById(entity);
	}
	
	/**
	 * 角色已选择用户
	 * @param entity
	 * @return
	 */
	public Page<SysUser> roleYxUser(SysUser entity){
		Page<SysUser> page = new Page<SysUser>();
		
		PageHelper.startPage(entity.getPage().intValue(), entity.getRows().intValue());
		List<SysUser> list = sysUserRoleDao.roleYxUser(entity);
		page.setRows(list);
		
		PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(list);
        page.setTotal(pageInfo.getTotal());
        
        page.setEntity(new SysUser());
		
		return page;
	}

	/**
	 * 角色未选择用户
	 * @param entity
	 * @return
	 */
	public Page<SysUser> roleWxUser(SysUser entity){
		Page<SysUser> page = new Page<SysUser>();
		
		PageHelper.startPage(entity.getPage().intValue(), entity.getRows().intValue());
		List<SysUser> list = sysUserRoleDao.roleWxUser(entity);
		page.setRows(list);
		
		PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(list);
        page.setTotal(pageInfo.getTotal());
        
        page.setEntity(new SysUser());
		
		return page;
	}
	
	/**
	 * 添加角色用户
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void addRoleUser(SysUserRole entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.preInsert();
				entity.setUserId(id);
				sysUserRoleDao.insert(entity);
			}
			
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_ + entity.getUserId());
			SysUser user = new SysUser();
			user.setId(entity.getUserId());
			user = sysUserDao.get(user);
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_NAME_ + user.getLoginId());
		}
	}
	
	/**
	 * 删除角色用户
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void deleteRoleUser(SysUserRole entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.setId(null);
				entity.setUserId(id);
				sysUserRoleDao.delete(entity);
			}
			
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_ + entity.getUserId());
			SysUser user = new SysUser();
			user.setId(entity.getUserId());
			user = sysUserDao.get(user);
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_NAME_ + user.getLoginId());
		}
	}
	
	/**
	 * 查询角色下的人员信息
	 * @param userRole
	 * @return
	 */
	public List<SysUser> querySysUserBySysUserRole(SysUserRole userRole){
		return sysUserRoleDao.querySysUserBySysUserRole(userRole);
	}
	
	
	/**
	 * 查询出用户未被授权的的角色
	 * @param entity
	 * @return Page<SysRole>
	 */
	public boolean querySysUserBySysUserRoleCount(SysUserRole entity){
		long a = sysUserRoleDao.querySysUserBySysUserRoleCount(entity);
		return a > 0;
	}
}
