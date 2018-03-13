/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.dao;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.sys.entity.SysNotice;

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
public interface ISysNoticeDao extends CrudDao<SysNotice>{
	 /**
	  * 查找未查看系统消息
	  * @param vo
	  * @return
	  */
	 public SysNotice findUnChecked(SysNotice vo);
	 
	 /**
	  * 更新已查看系统消息
	  * @param vo
	  */
	 public void updateChecked(SysNotice vo);
	 
	 /**
	  * 获取未查看系统消息
	  * @param entity
	  * @return
	  */
	 public String getUncheck(SysNotice entity);

}
