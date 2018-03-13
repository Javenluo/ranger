/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import java.util.List;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysBmfzr;

/**
 * <pre>
 * DAO接口
 * </pre>
 * @author xxx xxx@foresee.com.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @MyBatisDao
public interface ISysBmfzrDao extends CrudDao<SysBmfzr>{
	 
	 /**
	  * 获取部门负责人
	  * @param bpmBmfzr
	  * @return
	  */
	 List<SysBmfzr> queryBmfzrList(SysBmfzr sysBmfzr);
	 
}
