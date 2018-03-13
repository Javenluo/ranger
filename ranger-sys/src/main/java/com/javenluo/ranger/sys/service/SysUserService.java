/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.service;

import java.util.List;

import com.javenluo.ranger.common.service.CrudService;
import com.foresee.ranger.sys.entity.*;
import com.javenluo.ranger.sys.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foresee.fbidp.common.persistence.Page;
import com.foresee.fbidp.common.utils.CacheUtils;
import com.javenluo.ranger.sys.dao.ISysLoginDao;
import com.javenluo.ranger.sys.dao.ISysOrgUserDao;
import com.javenluo.ranger.sys.dao.ISysUserDao;
import com.javenluo.ranger.sys.dao.ISysUserRoleDao;
import com.javenluo.ranger.sys.dao.ISysWbxtDao;
import com.javenluo.ranger.sys.entity.SysUser;
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
public class SysUserService extends CrudService<ISysUserDao,SysUser> {
	
	@Autowired
	private ISysUserRoleDao sysUserRoleDao;
	@Autowired
	private ISysOrgUserDao sysOrgUserDao;
	@Autowired
	private ISysLoginDao sysLoginDao;
	@Autowired
	private ISysWbxtDao sysWbxtDao;
	
	public SysUser get(SysUser entity) {
		return super.get(entity);
	}
	
	public List<SysUser> findList(SysUser entity) {
		return super.findList(entity);
	}
	
	public Page<SysUser> findPage(SysUser entity) {
		return super.findPage(entity);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUser entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void insert(SysUser entity) {
		SysLogin sysLogin = new SysLogin(entity.getLoginId());
		sysLogin = sysLoginDao.get(sysLogin);
		SysLogin login = new SysLogin();
		String passwd;
		if(StringUtils.isNotEmpty(entity.getPasswd())){
			passwd = SystemService.entryptPassword(entity.getPasswd());
		} else {
			passwd = SystemService.getDefaultPassword();
		}
		// 校验该loginId是否存在登录账号表，已存在则不新增
		if(sysLogin == null){
			login.preInsert();
			login.setLoginId(entity.getLoginId());
			login.setPasswd(passwd);
			sysLoginDao.insert(login);
		}
		entity.setPasswd(passwd);
		entity.preInsert();
		dao.insert(entity);
	}
	
	/**
	 * 更新用户
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void update(SysUser entity) {
		entity.preUpdate();
		dao.update(entity);
		
		//清理缓存
		UserUtils.clearCache(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUser entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.setId(id);
				super.delete(entity);
				
				//清理缓存
				UserUtils.clearCache(entity);
			}
			
		}
	}
	
	/**
	 * 根据用户id更新用户密码
	 * @param entity 用户实体类
	 */
	@Transactional(readOnly = false)
	public void updatePasswordById(SysUser entity) {
		SysLogin login = new SysLogin();
		login.setPasswd(SystemService.entryptPassword(entity.getNewPasswd()));
		login.setLoginId(entity.getLoginId());
		
		sysLoginDao.updatePasswordById(login);
		
		if(sysWbxtDao.get(new SysWbxt(entity.getLoginId())) != null){
			SysWbxt sysWbxt = new SysWbxt();
			sysWbxt.setXtbm(entity.getLoginId());
			sysWbxt.setXtmm(entity.getNewPasswd());
			//同步更新外部注册密码
			sysWbxtDao.updatePassword(sysWbxt);
		}
		
		//清理缓存
		UserUtils.clearCache(entity);
	}
	
	/**
	 * 给用户赋予角色
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void addUserRole(SysUserRole entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.preInsert();
				entity.setRoleId(id);
				sysUserRoleDao.insert(entity);
			}
			
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_ + entity.getUserId());
			SysUser user = new SysUser();
			user.setId(entity.getUserId());
			user = this.get(user);
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_NAME_ + user.getLoginId());
		}
	}
	
	/**
	 * 删除用户角色
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void deleteUserRole(SysUserRole entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.setId(null);
				entity.setRoleId(id);
				sysUserRoleDao.delete(entity);
			}
			
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_ + entity.getUserId());
			SysUser user = new SysUser();
			user.setId(entity.getUserId());
			user = this.get(user);
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_NAME_ + user.getLoginId());
		}
	}
	
	/**
	 * 给用户添加组织机构
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void addOrgUser(SysOrgUser entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.preInsert();
				entity.setOrgId(id);
				sysOrgUserDao.insert(entity);
			}
			
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_ + entity.getUserId());
			SysUser user = new SysUser();
			user.setId(entity.getUserId());
			user = this.get(user);
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_NAME_ + user.getLoginId());
		}
		
	}
	
	/**
	 * 删除用户的组织机构
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void deleteOrgUser(SysOrgUser entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.setId(null);
				entity.setOrgId(id);
				sysOrgUserDao.delete(entity);
			}
			
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_ + entity.getUserId());
			SysUser user = new SysUser();
			user.setId(entity.getUserId());
			user = this.get(user);
			CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_NAME_ + user.getLoginId());
		}
	}
	
	/**
	 * 查询出用户的授权机构
	 * @param sysOrgUser
	 * @return
	 */
	public List<SysOrg> queryGrantOrgById(SysOrgUser sysOrgUser){
		return sysOrgUserDao.queryGrantOrgById(sysOrgUser);
	}
	
	/**
	 * 根据部门代码获取部门名称
	 * @return
	 */
	public String queryOrgNameByOrgCode(String orgCode){
		return sysOrgUserDao.queryOrgNameByOrgCode(orgCode);
	}
	
	/**
	 * 插入部门负责人
	 * @param bmfzr
	 */
	@Transactional(readOnly = false)
	public void insertBmfzr(SysBmfzr bmfzr) {
		bmfzr.preInsert();
		dao.insertBmfzr(bmfzr);
	}
	/**
	 * 查询部门负责人
	 * @param bmfzr
	 */
	public SysBmfzr findBmfzr(SysBmfzr bmfzr) {
		return dao.findBmfzr(bmfzr);
	}
	
	/**
	 * 删除部门负责人
	 * @param loginId
	 */
	@Transactional(readOnly = false)
	public void deleteBmfzr(String loginId) {
		
		dao.deleteBmfzr(loginId);
	}
	
	/**
	 * 校验用户重复
	 * @param user
	 * @return
	 */
	public Boolean userCount(SysUser user) {
		Long i = dao.userCount(user);
		
		return i>0;
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateName(SysUser user){
		dao.updateName(user);
	}

}
