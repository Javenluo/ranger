package com.javenluo.ranger.sys.utils;

import java.util.ArrayList;
import java.util.List;

import com.foresee.ranger.sys.dao.*;
import com.javenluo.ranger.sys.dao.*;
import com.javenluo.ranger.sys.security.SystemAuthorizingRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.foresee.fbidp.common.utils.CacheUtils;
import com.foresee.fbidp.common.utils.SpringContextHolder;
import com.javenluo.ranger.sys.dao.ISysRoleDao;
import com.javenluo.ranger.sys.entity.SysMenu;
import com.javenluo.ranger.sys.entity.SysOrg;
import com.javenluo.ranger.sys.entity.SysOrgUser;
import com.javenluo.ranger.sys.entity.SysRole;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.entity.SysUserRole;


/**
 * 用户工具类
 * @author gulong
 * @version 2013-12-05
 */
public class UserUtils {

	private static ISysUserDao userDao = SpringContextHolder.getBean(ISysUserDao.class);
	private static ISysLoginDao loginDao = SpringContextHolder.getBean(ISysLoginDao.class);
	private static ISysOrgUserDao orgUserDao = SpringContextHolder.getBean(ISysOrgUserDao.class);
	private static ISysRoleDao roleDao = SpringContextHolder.getBean(ISysRoleDao.class);
	private static ISysUserRoleDao userRoleDao = SpringContextHolder.getBean(ISysUserRoleDao.class);
	private static ISysMenuDao menuDao = SpringContextHolder.getBean(ISysMenuDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static SysUser get(String id){
		SysUser user = (SysUser)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user ==  null){
			user = new SysUser();
			user.setId(id);
			user = userDao.get(user);
			if (user == null){
				return null;
			}
			
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(user.getId());
			List<SysRole> roleList = userRoleDao.queryGrantRoleById(userRole);
			user.setRoleList(roleList);
			
			SysOrgUser sysOrgUser = new SysOrgUser();
			sysOrgUser.setUserId(user.getId());
			List<SysOrg> list = orgUserDao.queryGrantOrgById(sysOrgUser);
			
			List<String> orgIds = new ArrayList<String>();
			List<String> orgCodes = new ArrayList<String>();
			List<String> fullOrgIds = new ArrayList<String>();
			List<String> fullOrgCodes = new ArrayList<String>();
			for (SysOrg sysOrg : list) {
				orgIds.add(sysOrg.getId());
				orgCodes.add(sysOrg.getOrgCode());
				fullOrgIds.add(sysOrg.getFullOrgIds());
				fullOrgCodes.add(sysOrg.getFullOrgCodes());
			}
/*			orgIds.add(user.getOrgId());
			orgCodes.add(user.getOrgCode());
			fullOrgCodes.add(user.getFullOrgCodes());*/
			
			user.setOrgIdList(orgIds);
			user.setOrgCodeList(orgCodes);
			user.setFullOrgIdList(fullOrgIds);
			user.setFullOrgCodeList(fullOrgCodes);
			
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginId(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName 登录名
	 * @return 取不到返回null
	 */
	public static SysUser getByLoginName(String loginName){
		SysUser user = loginDao.getByLoginName(new SysUser(null, loginName));
		if (user == null) {
			return null;
		}
		return user;
	}

	/**
	 * 根据登录名获取用户集合
	 * @param loginName 登录名
	 * @return
	 */
	public static List<SysUser> findByLoginName(String loginName){
		SysUser user = new SysUser(null, loginName);
		
		if(user.isAdmin() || StringUtils.equals("supadmin", loginName)){
			List<SysUser> list = new ArrayList<SysUser>();
			SysUser u = userDao.get(new SysUser("1",null));
			list.add(u);
			return list;
		}
		
		return userDao.getByLoginId(user);
	}
	
	/**
	 * 根据登录id获取用户
	 * @param loginId
	 * @return
	 */
	public static SysUser findByLoginId(String loginId){
		SysUser user = new SysUser();
		user.setLoginId(loginId);
		if(user.isAdmin() || StringUtils.equals("supadmin", loginId)){
			SysUser u = userDao.get(new SysUser("1",null));
			return u;
		}
		return userDao.get(user);
	}
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(SysUser user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginId());
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static SysUser getUser(){
		SystemAuthorizingRealm.Principal principal = getPrincipal();
		if (principal!=null){
			SysUser user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new SysUser();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new SysUser();
	}

	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<SysRole> getRoleList(){
		@SuppressWarnings("unchecked")
		List<SysRole> roleList = (List<SysRole>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			SysUser user = getUser();
			if (user.isAdmin()){
				roleList = roleDao.findAllList(new SysRole());
			}else{
				roleList = user.getRoleList();
			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<SysMenu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<SysMenu> menuList = (List<SysMenu>)getCache(CACHE_MENU_LIST);
		if (menuList == null){
			SysUser user = getUser();
			if (user.isAdmin()){
				SysMenu menu = new SysMenu();
				menuList = menuDao.findAllList(menu);
			}else{
				SysMenu m = new SysMenu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	

	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static SystemAuthorizingRealm.Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	/**
	 * 获取缓存
	 * @return
	 */
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}
	
}
