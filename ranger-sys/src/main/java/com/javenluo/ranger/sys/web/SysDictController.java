/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.entity.SysDict;
import com.javenluo.ranger.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foresee.fbidp.common.persistence.Page;

/**
 * <pre>
 * 数据字典Controller控制层类
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysDict")
public class SysDictController extends BaseController {

	@Autowired
	private SysDictService sysDictService;
	
	/**
	 * 进入查询界面
	 * @param sysDict SysDict 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return String
	 */
	@RequiresPermissions("sys:sysDict:view")
	@RequestMapping(value = {"list"})
	public String list(SysDict sysDict, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("sysDict", sysDict);
		//菜单功能帮助说明所需的菜单id
		model.addAttribute("menuId", sysDict.getId());
		return "modules/sys/sysDict/sysDictList";
	}
	
	/**
	 * 查询
	 * @param sysDict SysDict实体类 
	 * @param request
	 * @param response
	 * @return Page<SysDict> 分页
	 */
	@RequiresPermissions("sys:sysDict:view")
	@RequestMapping(value = {"query"})
	@ResponseBody
	public Page<SysDict> query(SysDict sysDict,HttpServletRequest request, HttpServletResponse response) {
		Page<SysDict> page = this.sysDictService.findPage(sysDict); 
		return page;
	}
	
	/**
	 * 查询
	 * @param sysDict SysDict实体类 
	 * @param request
	 * @param response
	 * @return Page<SysDict> 分页
	 */
	@RequiresPermissions("sys:sysDict:view")
	@RequestMapping(value = {"dictList"})
	@ResponseBody
	public List<SysDict> dictList(SysDict sysDict,HttpServletRequest request, HttpServletResponse response) {
		List<SysDict> list = this.sysDictService.findList(sysDict); 
		return list;
	}
	
	/**
	 * 新增
	 * @param SysDict SysDict实体类
	 * @param model Model
	 * @return Page<SysDict> 分页
	 */
	@RequiresPermissions("sys:sysDict:edit")
	@RequestMapping(value = "insert")
	@ResponseBody
	public Map<String,Object> insert(SysDict sysDict, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		if(sysDictService.dictCount(sysDict)){
			result.put("success", false);
			result.put("msg", "字典已存在，请勿重复添加");
		} else {
			this.sysDictService.insert(sysDict);
		}
		
		return result;
	}
	
	/**
	 * 修改
	 * @param SysDict SysDict实体类
	 * @param model Model
	 * @return Page<SysDict> 分页
	 */
	@RequiresPermissions("sys:sysDict:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String,Object> update(SysDict sysDict, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		
		if(sysDictService.dictCount(sysDict)){
			result.put("success", false);
			result.put("msg", "字典已存在，请勿重复添加");
		} else {
			this.sysDictService.update(sysDict);
		}
		
		return result;
	}
	
	/**
	 * 删除
	 * @param sysDict SysDict实体类 
	 * @param redirectAttributes RedirectAttributes
	 * @return String
	 */
	@RequiresPermissions("sys:sysDict:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(SysDict sysDict, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysDictService.delete(sysDict);
		
		return map;
	}

}
