/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.service;

import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javenluo.ranger.common.persistence.Page;
import com.javenluo.ranger.common.service.CrudService;
import com.javenluo.ranger.sys.dao.ISysOrgDao;
import com.javenluo.ranger.sys.entity.SysOrg;

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
public class SysOrgService extends CrudService<ISysOrgDao,SysOrg> {
	
	public SysOrg get(String id) {
		return super.get(id);
	}
	
	public List<SysOrg> findList(SysOrg entity) {
		return super.findList(entity);
	}
	
	public Page<SysOrg> findPage(SysOrg entity) {
		return super.findPage(entity);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOrg entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void insert(SysOrg entity) {
		entity.preInsert();
		
		entity.setFullOrgIds(entity.getFullOrgIds()+","+entity.getId());
		
		dao.insert(entity);
	}
	
	@Transactional(readOnly = false)
	public void update(SysOrg entity) {
		entity.preUpdate();
		dao.update(entity);
		
		//修改下级的FullOrgCodes和FullOrgNames
		if(!StringUtils.equals(entity.getFullOrgCodes(), entity.getOldFullOrgCodes())
				|| !StringUtils.equals(entity.getFullOrgNames(), entity.getOldFullOrgNames())){
			entity.setParentOrgCode(entity.getOrgCode());
			dao.updateFullOrgCodes(entity);
		}
		
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOrg entity) {
		super.delete(entity);
	}
	
	/**
	 * 根据顶层机构ID，获取所有一二级组织机构列表(系统管理员)
	 * @param topOrgId String
	 * @return List<SysOrg>
	 */
	public List<SysOrg> queryTopOrgListByAdminUser(String topOrgId){
		return dao.queryTopOrgListByAdminUser(topOrgId);
	}
	
	/**
	 * 根据顶层机构ID，获取所有一二级组织机构列表（普通用户）
	 * @param entity SysOrg
	 * @return List<SysOrg>
	 */
	public List<SysOrg> queryTopOrgListByCommonUser(SysOrg entity){
		return dao.queryTopOrgListByCommonUser(entity);
	}
	
	/**
	 * 根据市局orgCode获取机构列表
	 * @param orgCode
	 * @return
	 */
	public List<SysOrg> queryTopOrgListByShijuUser(String orgCode) {
		return dao.queryTopOrgListByShijuUser(orgCode);
	}
	
	/**
	 * 跟据机构获取上级税务机关的下级机构
	 * @param sysOrg
	 * @return
	 */
	public List<SysOrg> queryCurrentBmList(SysOrg sysOrg){
		return dao.queryCurrentBmList(sysOrg);
	}

	/**
	 * 根据parentCode查找子节点
	 * @param sysOrg
	 * @return
	 */
	public List<SysOrg> queryAddTreeList(SysOrg sysOrg) {
		return dao.queryAddTreeList(sysOrg);
	}
	
	/**
	 * 获取税务机关级别
	 * @param orgCode
	 * @return
	 */
	public SysOrg querySjswjg(String orgCode) {
		return dao.querySjswjg(orgCode);
		
	}
	
	/**
	 * 根据顶层机构OrgCode，获取所有组织机构列表
	 * @param topOrgCode
	 * @return
	 */
	public List<SysOrg> queryTopOrgListByTopOrgCode(String topOrgCode) {
		return dao.queryTopOrgListByTopOrgCode(topOrgCode);
	}
	
	/**
	 * 查询行政区划级次
	 * @param orgCode
	 * @return
	 */
	public SysOrg queryXzqhjc(String orgCode){
		return dao.queryXzqhjc(orgCode);
	}
	
	/**
	 * 校验用户重复
	 * @param user
	 * @return
	 */
	public Boolean orgCount(SysOrg org) {
		Long i = dao.orgCount(org);
		
		return i>0;
	}
	
}
