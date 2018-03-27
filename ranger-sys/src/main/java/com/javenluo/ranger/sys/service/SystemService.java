package com.javenluo.ranger.sys.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javenluo.ranger.common.config.Global;
import com.javenluo.ranger.common.security.shiro.session.SessionDAO;
import com.javenluo.ranger.common.service.BaseService;
import com.javenluo.ranger.common.service.ServiceException;
import com.javenluo.ranger.common.utils.CacheUtils;
import com.javenluo.ranger.common.utils.Digests;
import com.javenluo.ranger.common.utils.Encodes;
import com.javenluo.ranger.common.utils.StringUtils;
import com.javenluo.ranger.common.web.Servlets;
import com.javenluo.ranger.sys.dao.ISysMenuDao;
import com.javenluo.ranger.sys.dao.ISysRoleDao;
import com.javenluo.ranger.sys.dao.ISysUserDao;
import com.javenluo.ranger.sys.entity.SysMenu;
import com.javenluo.ranger.sys.entity.SysRole;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.security.SystemAuthorizingRealm;
import com.javenluo.ranger.sys.utils.LogUtils;
import com.javenluo.ranger.sys.utils.UserUtils;



/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author gulong
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private ISysUserDao userDao;
	@Autowired
	private ISysRoleDao roleDao;
	@Autowired
	private ISysMenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}


	//-- User Service --//
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public SysUser getUser(String id) {
		return UserUtils.get(id);
	}
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public SysUser getUserById(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public SysUser getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<SysUser> findUser(SysUser user){
		List<SysUser> list = userDao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> findUserByOfficeId(String officeId) {
		List<SysUser> list = (List<SysUser>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null){
			SysUser user = new SysUser();
			list = userDao.findUserByOrgId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}
	
	/**
	 * 保存用户
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void saveUser(SysUser user) {
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userDao.insert(user);
		}else{
			// 清除原用户机构用户缓存
			SysUser oldUser = userDao.get(user.getId());
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userDao.insertUserRole(user);
			}else{
				throw new ServiceException(user.getLoginId() + "没有设置角色！");
			}
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void updateUserInfo(SysUser user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * 删除用户
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void deleteUser(SysUser user) {
		userDao.delete(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
//	@Transactional(readOnly = false)
//	public void updatePasswordById(String id, String loginName, String newPassword) {
//		SysUser user = new SysUser(id);
//		user.setPasswd(entryptPassword(newPassword));
//		userDao.updatePasswordById(user);
//		// 清除用户缓存
//		user.setLoginId(loginName);
//		UserUtils.clearCache(user);
////		// 清除权限缓存
////		systemRealm.clearAllCachedAuthorizationInfo();
//	}
	
	/**
	 * 更新用户登录信息
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(SysUser user) {
/*		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());*/
		// 更新本次登录信息
		user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLastLoginTime(new Date());
		userDao.updateLoginInfo(user);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 系统默认的初始密码
	 * @return
	 */
	public static String getDefaultPassword(){
		return entryptPassword("111111");
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	/**
	 *  根据id获取角色
	 * @param id 角色id
	 * @return
	 */
	public SysRole getRole(String id) {
		return roleDao.get(id);
	}

	/**
	 * 根据角色名获取角色
	 * @param name 角色名
	 * @return
	 */
	public SysRole getRoleByName(String name) {
		SysRole r = new SysRole();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	/**
	 * 根据角色编码获取角色
	 * @param enname 角色编码
	 * @return
	 */
	public SysRole getRoleByEnname(String enname) {
		SysRole r = new SysRole();
		r.setCode(enname);
		return roleDao.getByCode(r);
	}
	
	/**
	 * 获取角色集合
	 * @param role
	 * @return
	 */
	public List<SysRole> findRole(SysRole role){
		return roleDao.findList(role);
	}
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<SysRole> findAllRole(){
		return UserUtils.getRoleList();
	}
	
	/**
	 * 保存角色
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void saveRole(SysRole role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOrg(role);
		/*if (role.getOfficeList().size() > 0){
			roleDao.insertRoleOffice(role);
		}*/
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}

	/**
	 * 删除角色
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void deleteRole(SysRole role) {
		roleDao.delete(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * 删除用户拥有的已有角色
	 * @param role 角色
	 * @param user 用户
	 * @return
	 */
	@Transactional(readOnly = false)
	public Boolean outUserInRole(SysRole role, SysUser user) {
		List<SysRole> roles = user.getRoleList();
		for (SysRole e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 分配角色给用户
	 * @param role 角色
	 * @param user 用户
	 * @return
	 */
	@Transactional(readOnly = false)
	public SysUser assignUserToRole(SysRole role, SysUser user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//
	
	/**
	 * 根据父id获取菜单
	 * @param menu 菜单
	 * @return
	 */
	public List<SysMenu> findMenuByParentId(SysMenu menu){
		return menuDao.findMenuByParentId(menu);
	}
	
	/**
	 * 根据id获取菜单
	 * @param id 菜单id
	 * @return
	 */
	public SysMenu getMenu(String id) {
		return menuDao.get(id);
	}

	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<SysMenu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	/**
	 * 保存菜单
	 * @param menu 菜单
	 */
	@Transactional(readOnly = false)
	public void saveMenu(SysMenu menu) {
		
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds(); 
		
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuDao.insert(menu);
		}else{
			menu.preUpdate();
			menuDao.update(menu);
		}
		
		// 更新子节点 parentIds
		SysMenu m = new SysMenu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<SysMenu> list = menuDao.findByParentIdsLike(m);
		for (SysMenu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	/**
	 * 更新菜单排序
	 * @param menu 菜单
	 */
	@Transactional(readOnly = false)
	public void updateMenuSort(SysMenu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	/**
	 * 删除菜单
	 * @param menu
	 */
	@Transactional(readOnly = false)
	public void deleteMenu(SysMenu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 --- "+Global.getConfig("productName")+" \r\n");
		sb.append("\r\n======================================================================\r\n");
		//System.out.println(sb.toString());
		return true;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		String pwd = SystemService.entryptPassword("1");
		System.out.println(pwd);
	}

	
	
}
