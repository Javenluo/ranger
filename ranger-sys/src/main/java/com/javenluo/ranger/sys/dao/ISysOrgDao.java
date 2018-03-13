/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import java.util.List;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysOrg;

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
public interface ISysOrgDao extends CrudDao<SysOrg>{

	 /**
	  * 修改全路径机构代码和名称
	  * @param sysOrg SysOrg
	  */
	 public void updateFullOrgCodes(SysOrg sysOrg);

	 /**
	 * 根据顶层机构ID，获取所有一二级组织机构列表(系统管理员)
	 * @param topOrgId String
	 * @return List<SysOrg>
	 */
	public List<SysOrg> queryTopOrgListByAdminUser(String topOrgId);
	
	/**
	 * 根据顶层机构ID，获取所有一二级组织机构列表(普通 用户)
	 * @param entity SysOrg
	 * @return List<SysOrg>
	 */
	public List<SysOrg> queryTopOrgListByCommonUser(SysOrg entity);
	
	/**
	 * 查询机构节点级别
	 * @param orgCode
	 * @return
	 */
	public SysOrg queryJdjb(String orgCode);
	
	/**
	 * 根据市局orgCode获取机构列表
	 * @param orgCode
	 * @return
	 */
	public List<SysOrg> queryTopOrgListByShijuUser(String orgCode);
	
	/**
	 * 跟据机构获取上级税务机关的下级机构
	 * @param sysOrg
	 * @return
	 */
	public List<SysOrg> queryCurrentBmList(SysOrg sysOrg);
	
	/**
	 * 根据parentCode查找子节点
	 * @param sysOrg
	 * @return
	 */
	public List<SysOrg> queryAddTreeList(SysOrg sysOrg);
	
	/**
	 * 获取税务机关级别
	 * @param orgCode
	 * @return
	 */
	public SysOrg querySjswjg(String orgCode);
	
	/**
	 * 根据顶层机构OrgCode，获取所有组织机构列表
	 * @param topOrgCode
	 * @return
	 */
	public List<SysOrg> queryTopOrgListByTopOrgCode(String topOrgCode);
	
	/**
	 * 查询行政区划级次
	 * @param orgCode
	 * @return
	 */
	public SysOrg queryXzqhjc(String orgCode);
	
	/**
	 * 机构名称做重复验证
	 * @param user
	 * @return
	 */
	public Long orgCount(SysOrg sysOrg);
}
