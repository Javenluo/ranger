/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.entity.SysRole;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping(value = "${adminPath}/sys/sysFirst")
public class SysFirstController extends BaseController {

	/**
	 * 进入首页界面
	 * @param sysRole SysRole实体类 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return String
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"list"})
	public String list(SysRole sysRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		//TODO:
		return "modules/sys/sysFirst/sysFirst";
	}
	
	

}
