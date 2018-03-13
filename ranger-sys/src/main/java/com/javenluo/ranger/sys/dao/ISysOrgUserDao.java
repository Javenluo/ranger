/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import java.util.List;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysOrg;
import com.javenluo.ranger.sys.entity.SysOrgUser;

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
public interface ISysOrgUserDao extends CrudDao<SysOrgUser>{

	 /**
	  * 查询出用户的授权机构
	  * @param sysOrgUser SysOrgUser
	  * @return List<SysOrg>
	  */
	 public List<SysOrg> queryGrantOrgById(SysOrgUser sysOrgUser);
	 
	 /**
	 * 根据部门代码获取部门名称
	 * @return
	 */
	 public String queryOrgNameByOrgCode(String orgCode);
}
