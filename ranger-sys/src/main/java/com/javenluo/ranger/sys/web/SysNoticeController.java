/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.entity.SysNotice;
import com.javenluo.ranger.sys.service.SysNoticeService;
import com.javenluo.ranger.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foresee.fbidp.common.persistence.Page;

/**
 * <pre>
 * Controller控制层类
 * </pre>
 * @author xxx xxx@foresee.com.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysNotice")
public class SysNoticeController extends BaseController {

	@Autowired
	private SysNoticeService sysNoticeService;
	
	/**
	 * 进入查询界面
	 * @param sysNotice SysNotice 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return String
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"list"})
	public String list(SysNotice sysNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("sysNotice", sysNotice);
		return "modules/sys/sysNotice/sysNoticeList";
	}
	
	/**
	 * 查询
	 * @param sysNotice SysNotice 
	 * @param request
	 * @param response
	 * @return Page<SysNotice>
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"query"})
	@ResponseBody
	public Page<SysNotice> query(SysNotice sysNotice,HttpServletRequest request, HttpServletResponse response) {
		sysNotice.setContactId(UserUtils.getUser().getLoginId());
		Page<SysNotice> page = this.sysNoticeService.findPage(sysNotice); 
		return page;
	}
	
    /**
	 * 新增
	 * @param entity SysNotice 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "insert")
	@ResponseBody
	public Map<String,Object> insert(SysNotice entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		this.sysNoticeService.insert(entity);
		
		return result;
	}
	
	/**
	 * 修改
	 * @param entity SysNotice 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String,Object> update(SysNotice entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		this.sysNoticeService.update(entity);
		
		return result;
	}
	
	/**
	 * 删除
	 * @param sysNotice SysNotice 
	 * @return String
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(SysNotice entity) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysNoticeService.delete(entity);
		
		return map;
	}

	
	/**
	 * 通过ID查询
	 * @param sysNotice SysNotice 
	 * @param request
	 * @return SysNotice
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"queryByID"})
	public String getByID(String id,HttpServletRequest request, Model model) {
		SysNotice tem = this.sysNoticeService.get(id);
		tem.setContent(StringEscapeUtils.unescapeHtml4(tem.getContent()));
		model.addAttribute("sysNotice",tem);
		
		SysNotice sysNotice = new SysNotice();  //查看后将状态变为1
		sysNotice.setId(id);
		sysNotice.setChecked("1");
		this.sysNoticeService.updateChecked(sysNotice);
		return "modules/sys/sysNotice/sysNoticeView";
	}
	
    
	
	@RequiresPermissions("user")
	@RequestMapping(value = {"getUncheck"})
	@ResponseBody
	public String getUncheck() {
		SysNotice sysNotice = new SysNotice();
		sysNotice.setContactId(UserUtils.getUser().getLoginId());
				
		return this.sysNoticeService.getUncheck(sysNotice);
	}
	

}
