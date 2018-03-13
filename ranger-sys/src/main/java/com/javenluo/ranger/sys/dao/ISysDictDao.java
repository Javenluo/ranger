/**
 * Copyright &copy; 2012-2016 <a href="www.foresee.com">Forsee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import java.util.List;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysDict;


/**
 * 字典DAO接口
 * @author gulong
 * @version 2014-05-16
 */
@MyBatisDao
public interface ISysDictDao extends CrudDao<SysDict> {

	public List<String> findTypeList(SysDict dict);
	
	/**
	 * 校验字典重复
	 * @param entity
	 * @return
	 */
	public Long dictCount(SysDict entity);
}
