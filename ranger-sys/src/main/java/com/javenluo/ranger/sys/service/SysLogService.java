/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javenluo.ranger.common.persistence.Page;
import com.javenluo.ranger.common.service.CrudService;
import com.javenluo.ranger.sys.dao.ISysLogDao;
import com.javenluo.ranger.sys.entity.SysLog;

/**
 * <pre>
 * Service服务类
 * </pre>
 * @author wujinghui wujinghui@foresee.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class SysLogService extends CrudService<ISysLogDao,SysLog> {
	
	public SysLog get(String id) {
		return super.get(id);
	}
	
	public List<SysLog> findList(SysLog entity) {
		return super.findList(entity);
	}

	public Page<SysLog> findPage(SysLog entity) {
		return super.findPage(entity);
	}
	
	@Transactional(readOnly = false)
	public void save(SysLog entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void insert(SysLog entity) {
		entity.preInsert();
		dao.insert(entity);
	}
	
	@Transactional(readOnly = false)
	public void update(SysLog entity) {
		entity.preUpdate();
		dao.update(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysLog entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.setId(id);
				super.delete(entity);
			}
		}
	}

}
