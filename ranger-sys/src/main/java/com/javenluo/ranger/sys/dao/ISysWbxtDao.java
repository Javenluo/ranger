/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysWbxt;

/**
 * <pre>
 * DAO接口
 * </pre>
 * @author mmr mmr@loafish.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @MyBatisDao
public interface ISysWbxtDao extends CrudDao<SysWbxt>{

	 /**
	 * 校验系统编码重复
	 * @param entity
	 * @return
	 */
	public Long xtbmCount(SysWbxt entity);

	
	/**
	 * 同步更新密码
	 * @param entity
	 */
	public void updatePassword(SysWbxt entity);

	/**
	 * 查询待办接口
	 * @param tenantId
	 * @return
	 */
	public SysWbxt queryDbjk(SysWbxt sysWbxt);
}
