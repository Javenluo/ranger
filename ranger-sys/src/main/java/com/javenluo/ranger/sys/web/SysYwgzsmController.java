/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javenluo.ranger.common.persistence.Page;
import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.entity.SysYwgzsm;
import com.javenluo.ranger.sys.service.SysYwgzsmService;

/**
 * <pre>
 * Controller控制层类
 * </pre>
 * @author mmr mmr@loafish.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysYwgzsm")
public class SysYwgzsmController extends BaseController {

	@Autowired
	private SysYwgzsmService sysYwgzsmService;
	
	/**
	 * 进入查询界面
	 * @param sysYwgzsm SysYwgzsm 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return String
	 */
	@RequiresPermissions("sys:sysYwgzsm:view")
	@RequestMapping(value = {"list"})
	public String list(SysYwgzsm sysYwgzsm, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("sysYwgzsm", sysYwgzsm);
		//菜单功能帮助说明所需的菜单id
		model.addAttribute("menuId", sysYwgzsm.getId());
		return "modules/sys/sysYwgzsm/sysYwgzsmList";
	}
	
	/**
	 * 查询
	 * @param sysYwgzsm SysYwgzsm 
	 * @param request
	 * @param response
	 * @return Page<SysYwgzsm>
	 */
	@RequiresPermissions("sys:sysYwgzsm:view")
	@RequestMapping(value = {"query"})
	@ResponseBody
	public Page<SysYwgzsm> query(SysYwgzsm sysYwgzsm,HttpServletRequest request, HttpServletResponse response) {
		Page<SysYwgzsm> page = this.sysYwgzsmService.findPage(sysYwgzsm); 
		for(SysYwgzsm vo:page.getRows()){
			vo.setYwgzSmFwb(StringEscapeUtils.unescapeHtml4(vo.getYwgzSmFwb()));
		}
		return page;
	}
	
    /**
	 * 新增
	 * @param entity SysYwgzsm 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysYwgzsm:edit")
	@RequestMapping(value = "insert")
	@ResponseBody
	public Map<String,Object> insert(SysYwgzsm entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		this.sysYwgzsmService.insert(entity);
		
		return result;
	}
	
	/**
	 * 修改
	 * @param entity SysYwgzsm 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysYwgzsm:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String,Object> update(SysYwgzsm entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		this.sysYwgzsmService.update(entity);
		
		return result;
	}
	
	/**
	 * 删除
	 * @param sysYwgzsm SysYwgzsm 
	 * @return String
	 */
	@RequiresPermissions("sys:sysYwgzsm:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(SysYwgzsm entity) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysYwgzsmService.delete(entity);
		
		return map;
	}

	/**
	 * 检查编码是否已存在
	 * @param sysYwgzsm SysYwgzsm 
	 * @return String
	 */
	@RequiresPermissions("sys:sysYwgzsm:edit")
	@RequestMapping(value = "checkYwgzBm")
	@ResponseBody
	public Map<String,Object> checkYwgzBm(SysYwgzsm entity) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", false);
		
		int i = this.sysYwgzsmService.checkYwgzBm(entity);
		if(i==0){
			map.put("success", true);
		}
		return map;
	}
}
