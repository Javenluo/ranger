/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javenluo.ranger.common.persistence.Page;
import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.entity.SysWbxt;
import com.javenluo.ranger.sys.service.SysWbxtService;

/**
 * <pre>
 * Controller控制层类
 * </pre>
 * @author huangyufu huangyufu@foresee.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysWbxt")
public class SysWbxtController extends BaseController {

	@Autowired
	private SysWbxtService sysWbxtService;
	
	/**
	 * 进入查询界面
	 * @param sysWbxt SysWbxt 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return String
	 */
	@RequiresPermissions("sys:sysWbxt:view")
	@RequestMapping(value = {"list"})
	public String list(SysWbxt sysWbxt, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("sysWbxt", sysWbxt);
		//菜单功能帮助说明所需的菜单id
		model.addAttribute("menuId", sysWbxt.getId());
		return "modules/sys/sysWbxt/sysWbxtList";
	}
	
	/**
	 * 查询
	 * @param sysWbxt SysWbxt 
	 * @param request
	 * @param response
	 * @return Page<SysWbxt>
	 */
	@RequiresPermissions("sys:sysWbxt:view")
	@RequestMapping(value = {"query"})
	@ResponseBody
	public Page<SysWbxt> query(SysWbxt sysWbxt,HttpServletRequest request, HttpServletResponse response) {
		Page<SysWbxt> page = this.sysWbxtService.findPage(sysWbxt); 
		return page;
	}
	
    /**
	 * 新增
	 * @param entity SysWbxt 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysWbxt:edit")
	@RequestMapping(value = "insert")
	@ResponseBody
	public Map<String,Object> insert(SysWbxt entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		if(sysWbxtService.xtbmCount(entity)){
			result.put("success", false);
			result.put("msg", "系统编码重复，请重新输入！");
		} else {
			this.sysWbxtService.insert(entity);
		}
		
		return result;
	}
	
	/**
	 * 修改
	 * @param entity SysWbxt 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysWbxt:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String,Object> update(SysWbxt entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		this.sysWbxtService.update(entity);
		
		return result;
	}
	
	/**
	 * 删除
	 * @param sysWbxt SysWbxt 
	 * @return String
	 */
	@RequiresPermissions("sys:sysWbxt:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(SysWbxt entity) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysWbxtService.delete(entity);
		
		return map;
	}

}
