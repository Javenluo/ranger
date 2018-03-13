/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javaluo.ranger.rest.dao;

import java.util.List;

import com.foresee.fbidp.common.persistence.CrudDao;
import com.foresee.fbidp.common.persistence.annotation.MyBatisDao;
import com.javaluo.ranger.rest.entity.SysToken;

/**
 * <pre>
 * DAO接口
 * &#64;author gulong 
 * &#64;version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@MyBatisDao
public interface ISysTokenDao extends CrudDao<SysToken> {

	/**
	 * 根据key删除
	 * @param key
	 */
	public void deleteByKey(String key);

	/**
	 * 根据token删除
	 * @param token
	 */
	public void deleteByToken(String token);

	/**
	 * 根据key修改
	 * @param sysToken
	 */
	public void updateByKey(SysToken sysToken);

	/**
	 * 根据Token修改
	 * @param sysToken
	 */
	public void updateByToken(SysToken sysToken);

	/**
	 * 根据Token获取数据
	 * @param sysToken
	 * @return List<String>
	 */
	public List<String> getKeyByToken(SysToken sysToken);

}
