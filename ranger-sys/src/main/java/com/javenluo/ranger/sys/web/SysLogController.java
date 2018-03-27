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
import com.javenluo.ranger.sys.entity.SysLog;
import com.javenluo.ranger.sys.service.SysLogService;

/**
 * <pre>
 * 系统日志Controller控制层类
 * </pre>
 * @author wujinghui wujinghui@foresee.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysLog")
public class SysLogController extends BaseController {

	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 进入查询界面
	 * @param sysLog SysLog实体类
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return String
	 */
	@RequiresPermissions("sys:sysLog:view")
	@RequestMapping(value = {"list"})
	public String list(SysLog sysLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("sysLog", sysLog);
		//菜单功能帮助说明所需的菜单id
		model.addAttribute("menuId", sysLog.getId());
		return "modules/sys/sysLog/sysLogList";
	}
	
	/**
	 * 查询
	 * @param sysLog SysLog实体类 
	 * @param request
	 * @param response
	 * @return Page<SysLog> 分页
	 */
	@RequiresPermissions("sys:sysLog:view")
	@RequestMapping(value = {"query"})
	@ResponseBody
	public Page<SysLog> query(SysLog sysLog,HttpServletRequest request, HttpServletResponse response) {
		Page<SysLog> page = this.sysLogService.findPage(sysLog); 
		return page;
	}
	
    /**
	 * 新增
	 * @param entity SysLog实体类 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysLog:edit")
	@RequestMapping(value = "insert")
	@ResponseBody
	public Map<String,Object> insert(SysLog entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		this.sysLogService.insert(entity);
		
		return result;
	}
	
	/**
	 * 修改更新
	 * @param entity SysLog实体类 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysLog:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String,Object> update(SysLog entity) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		this.sysLogService.update(entity);
		
		return result;
	}
	
	/**
	 * 删除
	 * @param sysLog SysLog实体类 
	 * @return String
	 */
	@RequiresPermissions("sys:sysLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(SysLog entity) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysLogService.delete(entity);
		
		return map;
	}

}
