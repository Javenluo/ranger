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
import com.javenluo.ranger.sys.dao.ISysYwgzsmDao;
import com.javenluo.ranger.sys.entity.SysYwgzsm;

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
public class SysYwgzsmService extends CrudService<ISysYwgzsmDao,SysYwgzsm> {
	
	public SysYwgzsm get(String id) {
		return super.get(id);
	}
	
	public List<SysYwgzsm> findList(SysYwgzsm entity) {
		return super.findList(entity);
	}
	
	public Page<SysYwgzsm> findPage(SysYwgzsm entity) {
		return super.findPage(entity);
	}
	
	@Transactional(readOnly = false)
	public void save(SysYwgzsm entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void insert(SysYwgzsm entity) {
		entity.preInsert();
		dao.insert(entity);
	}
	
	/**
	 * 更新业务规则
	 * @param entity 业务规则实体类
	 */
	@Transactional(readOnly = false)
	public void update(SysYwgzsm entity) {
		entity.preUpdate();
		dao.update(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysYwgzsm entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.setId(id);
				super.delete(entity);
			}
		}
	}

	/**
	 * 检测业务规则编码
	 * @param entity 业务规则实体类
	 * @return
	 */
	@Transactional(readOnly = false)
	public int checkYwgzBm(SysYwgzsm entity) {
		int i = dao.checkYwgzBm(entity);
		return i;
	}

}
