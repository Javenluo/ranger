/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.javenluo.ranger.common.persistence.Page;
import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.service.SysUserService;
import com.javenluo.ranger.sys.utils.UserUtils;

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
@RequestMapping(value = "${adminPath}/sys/sysUserAuthority")
public class SysUserAuthorityController extends BaseController {
	
	@Autowired
	SysUserService sysUserService;
	
	/**
	 * 进入查询界面
	 * @param sysUser SysUser 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return String
	 */
	@RequiresPermissions("sys:sysUser:view")
	@RequestMapping(value = {"list"})
	public String list(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("sysUser", sysUser);
		//菜单功能帮助说明所需的菜单id
		model.addAttribute("menuId", sysUser.getId());
		
		SysUser u = UserUtils.getUser();
		model.addAttribute("fullOrgCodes", StringUtils.isEmpty(u.getFullOrgCodes())?topOrgCode:u.getFullOrgCodes());
		model.addAttribute("orgId", StringUtils.isEmpty(u.getOrgId())?topOrgId:u.getOrgId());
		
		return "modules/sys/sysUserAuthority/sysUserAuthorityList";
	}
	
	/**
	 * 查询
	 * @param sysUser SysUser 
	 * @param request
	 * @param response
	 * @return Page<SysUser>
	 */
	@RequiresPermissions("sys:sysUser:view")
	@RequestMapping(value = {"query"})
	@ResponseBody
	public Page<SysUser> query(SysUser sysUser,HttpServletRequest request, HttpServletResponse response) {
		sysUser.setFullOrgIds(sysUser.getOrgId());
		sysUser.setOrgId(null);
		sysUser.setFullOrgCodes(null);
		Page<SysUser> page = this.sysUserService.findPage(sysUser); 
		return page;
	}

}
