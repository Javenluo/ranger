/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.util.StringUtil;
import com.javenluo.ranger.common.persistence.Page;
import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.entity.SysMenu;
import com.javenluo.ranger.sys.service.SysMenuService;

/**
 * <pre>
 * Controller控制层类
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysMenu")
public class SysMenuController extends BaseController {

	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 进入查询界面
	 * @param sysMenu SysMenu 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return String
	 */
	@RequiresPermissions("sys:sysMenu:view")
	@RequestMapping(value = {"list"})
	public String list(SysMenu sysMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("sysMenu", sysMenu);
		//菜单功能帮助说明所需的菜单id
		model.addAttribute("menuId", sysMenu.getId());
		return "modules/sys/sysMenu/sysMenuList";
	}
	
	/**
	 * 查询
	 * @param sysMenu SysMenu 
	 * @param request
	 * @param response
	 * @return Page<SysMenu>
	 */
	@RequiresPermissions("sys:sysMenu:view")
	@RequestMapping(value = {"query"})
	@ResponseBody
	public Page<SysMenu> query(SysMenu sysMenu,HttpServletRequest request, HttpServletResponse response) {
		Page<SysMenu> page = this.sysMenuService.findPage(sysMenu); 
		return page;
	}
	
	/**
	 * 新增
	 * @param sysMenu SysMenu 
	 * @param model Model
	 * @return Page<SysMenu>
	 */
	@RequiresPermissions("sys:sysMenu:edit")
	@RequestMapping(value = "insert")
	@ResponseBody
	public Map<String,Object> insert(SysMenu sysMenu, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		
		sysMenu.setImageUrl(sysMenu.getImageUrl().replace("amp;", ""));
		if(sysMenuService.menuCount(sysMenu)){
			result.put("success", false);
			result.put("msg", "菜单名称已存在，请勿重复添加");
		} else {
			this.sysMenuService.insert(sysMenu);
		}
		
		return result;
	}
	
	/**
	 * 修改
	 * @param sysMenu SysMenu 
	 * @param model Model
	 * @return Page<SysMenu>
	 */
	@RequiresPermissions("sys:sysMenu:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String,Object> update(SysMenu sysMenu, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		
		sysMenu.setImageUrl(sysMenu.getImageUrl().replace("amp;", ""));
		if(sysMenuService.menuCount(sysMenu)){
			result.put("success", false);
			result.put("msg", "菜单名称已存在，请勿重复添加");
		} else {
			this.sysMenuService.update(sysMenu);
		}
		
		return result;
	}
	
	/**
	 * 删除
	 * @param sysMenu SysMenu 
	 * @param redirectAttributes RedirectAttributes
	 * @return String
	 */
	@RequiresPermissions("sys:sysMenu:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(SysMenu sysMenu, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysMenuService.delete(sysMenu);
		
		return map;
	}
	
	/**
	 * 获取登录用户所拥有的菜单
	 * @return List<Map<String,Object>>获取用户菜单集合
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "getMenus")
	@ResponseBody
	public List<Map<String,Object>> getMenus(){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		
		result = this.sysMenuService.getMenus();
		
		return result;
	}
	
	/**
	 * 获取菜单树型结构
	 * @param id String父ID
	 * @return List<SysMenu> 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "getMenuListByParentId")
	@ResponseBody
	public List<SysMenu> getMenuListByParentId(String id){
		List<SysMenu> resultList = new ArrayList<SysMenu>();
		String parentId = id;
		if(StringUtils.isEmpty(parentId)){ //如果未传值，视为查找顶层菜单
			SysMenu mtop = new SysMenu();
			mtop.setName("菜单树");
			mtop.setId("0");
			mtop.setParentId("");
			mtop.setParentIds("");
			mtop.setVisible("2");
			mtop.setState("open");
			resultList.add(mtop);
			
			SysMenu vo = new SysMenu();
			vo.setParentId(mtop.getId());
			mtop.setChildren(this.sysMenuService.findList(vo));
		}else{
			SysMenu vo = new SysMenu();
			vo.setParentId(parentId);
			resultList = this.sysMenuService.findList(vo);
		}
		return resultList;
	}

	/**
	 * 根据菜单id查询帮助说明
	 * @param sysMenu
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequiresPermissions("sys:sysMenu:view")
	@RequestMapping(value = {"queryBzsm"})
	@ResponseBody
	public String queryBzsm(SysMenu sysMenu,HttpServletRequest request, HttpServletResponse response) {
		String bzsm = this.sysMenuService.findBzsmById(sysMenu);
		if(StringUtil.isEmpty(bzsm)){
			bzsm = " ";
		}
		bzsm = StringEscapeUtils.unescapeHtml4(bzsm);
		return bzsm;
	}
	
	/**
	 * 根据菜单id修改帮助说明
	 * @param sysMenu SysMenu 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysMenu:edit")
	@RequestMapping(value = "updateBzsm")
	@ResponseBody
	public Boolean updateBzsm(SysMenu sysMenu) {
		int a = this.sysMenuService.updateBzsmById(sysMenu);
		if(a>0){
			return true;
		}
		return false;
	}
}
