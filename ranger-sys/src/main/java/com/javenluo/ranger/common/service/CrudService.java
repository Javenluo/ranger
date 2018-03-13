/**
 * Copyright &copy; 2012-2016 <a href="www.foresee.com">Forsee</a> All rights reserved.
 */
package com.javenluo.ranger.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.DataEntity;
import com.javenluo.ranger.common.persistence.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
 * <pre>
 * Service基类
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 * @param <D>
 * @param <T>
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}
	
	/**
	 * 分页查询数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(T entity) {
		Page<T> page = new Page<T>();
		
		PageHelper.startPage(entity.getPage().intValue(), entity.getRows().intValue());
		List<T> list = dao.findList(entity);
		page.setRows(list);
		
		PageInfo<T> pageInfo = new PageInfo<T>(list);
        page.setTotal(pageInfo.getTotal());
        
        page.setEntity(entity);
		
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (StringUtils.isEmpty(entity.getId())){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void insert(T entity) {
		dao.insert(entity);
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

}
