/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.service;

import java.util.List;

import com.javenluo.ranger.common.service.CrudService;
import com.javenluo.ranger.sys.entity.SysNotice;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foresee.fbidp.common.persistence.Page;
import com.javenluo.ranger.sys.dao.ISysNoticeDao;

/**
 * <pre>
 * Service服务类
 * </pre>
 * @author xxx xxx@foresee.com.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class SysNoticeService extends CrudService<ISysNoticeDao,SysNotice> {
	
	public SysNotice get(String id) {
		return super.get(id);
	}
	
	public List<SysNotice> findList(SysNotice entity) {
		return super.findList(entity);
	}
	
	public Page<SysNotice> findPage(SysNotice entity) {
		Page<SysNotice> page = super.findPage(entity);
		//拼接字符串，显示纯文本
		for(SysNotice s:page.getRows()){
			String  str = s.getContent();
			String content = StringEscapeUtils.unescapeHtml4(str);
			int fir = content.indexOf("<");
			String fmsg = content;
			if (fir > 0) {
				fmsg = content.substring(0, fir);
			}
			int start = content.indexOf(">") + 1;
			if(start < 0){
				continue;
			}
			int end = content.lastIndexOf("】") + 1;
			if(end < 0){
				continue;
			}
			content = content.substring(start, end);
			s.setContent(fmsg + content);
		}
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(SysNotice entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void insert(SysNotice entity) {
		entity.preInsert();
		dao.insert(entity);
	}
	
	@Transactional(readOnly = false)
	public void update(SysNotice entity) {
		entity.preUpdate();
		dao.update(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysNotice entity) {
		if(StringUtils.isNotEmpty(entity.getIds())){
			String[] ids = entity.getIds().split("&amp;");
			for (String id : ids) {
				entity.setId(id);
				super.delete(entity);
			}
		}
	}
	
	/**
	 * 更新已查看系统消息
	 * @param entity
	 */
	 @Transactional(readOnly = false)
	 public void updateChecked(SysNotice entity){
		 entity.preUpdate();
			dao.updateChecked(entity);
	 };
	 
	 /**
	  * 获取未查看系统消息
	  * @param entity
	  * @return
	  */
	 public String getUncheck(SysNotice entity){
			return dao.getUncheck(entity);
	 };
}
