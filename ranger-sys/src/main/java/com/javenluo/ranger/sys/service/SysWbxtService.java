/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.service;

import java.util.List;

import com.javenluo.ranger.common.service.CrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foresee.fbidp.common.config.Global;
import com.foresee.fbidp.common.persistence.Page;
import com.foresee.fbidp.common.utils.Encodes;
import com.javenluo.ranger.sys.dao.ISysUserDao;
import com.javenluo.ranger.sys.dao.ISysWbxtDao;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.entity.SysUserRole;
import com.javenluo.ranger.sys.entity.SysWbxt;

/**
 * <pre>
 * Service服务类
 * </pre>
 * @author mmr mmr@loafish.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class SysWbxtService extends CrudService<ISysWbxtDao,SysWbxt> {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ISysUserDao sysUserDao;
	
	public SysWbxt get(String id) {
		return super.get(id);
	}
	
	public List<SysWbxt> findList(SysWbxt entity) {
		return super.findList(entity);
	}
	
	public Page<SysWbxt> findPage(SysWbxt entity) {
		return super.findPage(entity);
	}
	
	@Transactional(readOnly = false)
	public void save(SysWbxt entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void insert(SysWbxt entity) {
		entity.preInsert();
		//加密密码
		if(StringUtils.isNotEmpty(entity.getJkmm())){
			String pwd = Encodes.encodeBase64(entity.getJkmm());
			entity.setJkmm(pwd);
		}
		dao.insert(entity);
		
		SysUser user = new SysUser();
		user.preInsert();
		user.setLoginId(entity.getXtbm());
		user.setName(entity.getXtmc());
		user.setPasswd(entity.getXtmm());
		user.setOrgId(Global.getConfig("fbidp.top.orgId")); // 默认顶级机构
		sysUserService.insert(user); // 新增角色
		
		
		SysUserRole userRole = new SysUserRole();
		userRole.setIds("1"); // 默认权限
		userRole.setUserId(user.getId());
		sysUserService.addUserRole(userRole); // 角色授权
	}
	
	/**
	 * 更新外部系统
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void update(SysWbxt entity) {
		entity.preUpdate();
		//加密密码
		if(StringUtils.isNotEmpty(entity.getJkmm())){
			String pwd = Encodes.encodeBase64(entity.getJkmm());
			entity.setJkmm(pwd);
		}
		dao.update(entity);
		
		SysUser user = new SysUser();
		user.preUpdate();
		user.setLoginId(entity.getXtbm());
		user.setName(entity.getXtmc());
		sysUserService.updateName(user); // 更新用户信息
	}
	
	@Transactional(readOnly = false)
	public void delete(SysWbxt entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.setId(id);
				super.delete(entity);
			}
		}
	}
	
	/**
	 * 校验系统编码不重复
	 * @param entity
	 * @return
	 */
	public Boolean xtbmCount(SysWbxt entity) {
		Long i = dao.xtbmCount(entity);
		return i > 0;
	}

}
