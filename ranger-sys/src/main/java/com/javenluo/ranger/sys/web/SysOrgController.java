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

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javenluo.ranger.common.persistence.Page;
import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.entity.SysOrg;
import com.javenluo.ranger.sys.service.SysOrgService;

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
@RequestMapping(value = "${adminPath}/sys/sysOrg")
public class SysOrgController extends BaseController {

	@Autowired
	private SysOrgService sysOrgService;
	@Value("${fbidp.top.orgCode}")
	private String topOrgCode;
	
	/**
	 * 进入查询界面
	 * @param sysOrg SysOrg 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return String
	 */
	@RequiresPermissions("sys:sysOrg:view")
	@RequestMapping(value = {"list"})
	public String list(SysOrg sysOrg, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("sysOrg", sysOrg);
		//菜单功能帮助说明所需的菜单id
		model.addAttribute("menuId", sysOrg.getId());
		return "modules/sys/sysOrg/sysOrgList";
	}
	
	/**
	 * 查询
	 * @param sysOrg SysOrg 
	 * @param request
	 * @param response
	 * @return Page<SysOrg>
	 */
	@RequiresPermissions("sys:sysOrg:view")
	@RequestMapping(value = {"query"})
	@ResponseBody
	public Page<SysOrg> query(SysOrg sysOrg,HttpServletRequest request, HttpServletResponse response) {
		Page<SysOrg> page = this.sysOrgService.findPage(sysOrg); 
		return page;
	}
	
	/**
	 * 新增
	 * @param sysOrg SysOrg 
	 * @param model Model
	 * @return Page<SysOrg>
	 */
	@RequiresPermissions("sys:sysOrg:edit")
	@RequestMapping(value = "insert")
	@ResponseBody
	public Map<String,Object> insert(SysOrg sysOrg, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		String newFullOrgCodes = sysOrg.getFullOrgCodes()+ ","+sysOrg.getOrgCode();
		sysOrg.setFullOrgCodes(newFullOrgCodes);
		
		String newFullOrgNames = sysOrg.getFullOrgNames()+ "|"+sysOrg.getOrgShortName();
		sysOrg.setFullOrgNames(newFullOrgNames);
		
		if(sysOrgService.orgCount(sysOrg)){
			result.put("success", false);
			result.put("msg", "机构名称已存在，请勿重复添加");
		} else {
			this.sysOrgService.insert(sysOrg);
		}
		
		return result;
	}
	
	/**
	 * 修改
	 * @param sysOrg SysOrg 
	 * @param model Model
	 * @return Page<SysOrg>
	 */
	@RequiresPermissions("sys:sysOrg:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String,Object> update(SysOrg sysOrg, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		//1:如果机构编码有变
		if(!StringUtils.equals(sysOrg.getOrgCode(), sysOrg.getOldOrgCode())){
			String newFullOrgCodes = sysOrg.getFullOrgCodes().replace(","+sysOrg.getOldOrgCode(), ","+sysOrg.getOrgCode());
			sysOrg.setFullOrgCodes(newFullOrgCodes);
		}
		//2:如果机构名称有变
		if(!StringUtils.equals(sysOrg.getOrgShortName(), sysOrg.getOldOrgShortName())){
			String newFullOrgNames = sysOrg.getFullOrgNames().replace("|"+sysOrg.getOldOrgShortName(), "|"+sysOrg.getOrgShortName());
			sysOrg.setFullOrgNames(newFullOrgNames);
		}
		//TODO 3:如果所属上级机构有变
		if(StringUtils.equals(sysOrg.getOrgShortName(), sysOrg.getOldOrgShortName())){
			
		}
		
		if(sysOrgService.orgCount(sysOrg)){
			result.put("success", false);
			result.put("msg", "机构名称已存在，请勿重复添加");
		} else {
			this.sysOrgService.update(sysOrg);
		}
		
		return result;
	}
	
	/**
	 * 删除
	 * @param sysOrg SysOrg 
	 * @param redirectAttributes RedirectAttributes
	 * @return String
	 */
	@RequiresPermissions("sys:sysOrg:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(SysOrg sysOrg, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysOrgService.delete(sysOrg);
		
		return map;
	}
	
	/**
	 * 获取机构树型结构
	 * @param id String父ID
	 * @return List<SysMenu> 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "getOrgListByParentId")
	@ResponseBody
	public List<SysOrg> getOrgListByParentId(String id){
		List<SysOrg> resultList = new ArrayList<SysOrg>();
		String parentId = id;
		if(StringUtils.isEmpty(parentId)){ //如果未传值，视为查找顶层机构
			
			SysOrg mtop = new SysOrg();
			
			SysOrg vo = new SysOrg();
			vo.setOrgCode(this.topOrgCode);
			List<SysOrg> list = this.sysOrgService.findList(vo);
			if(!list.isEmpty()){
				mtop = list.get(0);
				mtop.setState("open");
			}
			
			vo = new SysOrg();
			vo.setParentOrgId(mtop.getId());
			mtop.setChildren(this.sysOrgService.findList(vo));
			
			resultList.add(mtop);
		}else{
			SysOrg vo = new SysOrg();
			vo.setParentOrgId(parentId);
			resultList = this.sysOrgService.findList(vo);
		}
		return resultList;
	}
	
	/**
	 * 检查机构编码是否已经存
	 * @param sysOrg SysOrg
	 * @return boolean true表示存在
	 */
	@RequiresPermissions("sys:sysOrg:edit")
	@RequestMapping(value = {"checkExsit4OrgCode"})
	@ResponseBody
	public boolean checkExsit4OrgCode(SysOrg sysOrg){
		SysOrg entry = new SysOrg();
		entry.setOrgCode(sysOrg.getOrgCode());
		
		List<SysOrg> list = this.sysOrgService.findList(entry);
		return !list.isEmpty();
	}

}
