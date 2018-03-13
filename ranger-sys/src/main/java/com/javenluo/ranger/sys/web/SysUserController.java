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

import com.javenluo.ranger.common.utils.excel.ExportExcel;
import com.javenluo.ranger.common.utils.excel.ImportExcel;
import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.service.SysOrgService;
import com.javenluo.ranger.sys.service.SysRoleService;
import com.javenluo.ranger.sys.service.SysUserService;
import com.javenluo.ranger.sys.service.SystemService;
import com.javenluo.ranger.sys.utils.UserUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.druid.util.StringUtils;
import com.foresee.fbidp.common.persistence.Page;
import com.javenluo.ranger.sys.entity.SysBmfzr;
import com.javenluo.ranger.sys.entity.SysOrg;
import com.javenluo.ranger.sys.entity.SysOrgUser;
import com.javenluo.ranger.sys.entity.SysRole;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.entity.SysUserRole;
import com.google.common.collect.Lists;

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
@RequestMapping(value = "${adminPath}/sys/sysUser")
public class SysUserController extends BaseController {
	
	private Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysOrgService sysOrgService;
	
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
		model.addAttribute("menuId", sysUser.getId());
		
		SysUser u = UserUtils.getUser();
		model.addAttribute("fullOrgCodes", StringUtils.isEmpty(u.getFullOrgCodes())?topOrgCode:u.getFullOrgCodes());
		model.addAttribute("orgId", StringUtils.isEmpty(u.getOrgId())?topOrgId:u.getOrgId());
		
		return "modules/sys/sysUser/sysUserList";
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
		Page<SysUser> page = this.sysUserService.findPage(sysUser); 
		return page;
	}
	
	/**
	 * 查询
	 * @param sysUser SysUser 
	 * @return SysUser
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"get"})
	@ResponseBody
	public SysUser get(SysUser sysUser){
		return this.sysUserService.get(sysUser);
	}
	
	/**
	 * 验证旧密码输入是否正确
	 * @param sysUser SysUser 
	 * @return boolean
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"checkPassword"})
	@ResponseBody
	public boolean checkPassword(SysUser sysUser){
		SysUser tempUser = this.sysUserService.get(sysUser);
		return SystemService.validatePassword(sysUser.getPasswd(), tempUser.getPasswd());
	}
	
	/**
	 * 新增
	 * @param sysUser SysUser 
	 * @param model Model
	 * @return Page<SysUser>
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = "insert")
	@ResponseBody
	public Map<String,Object> insert(SysUser sysUser, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		if(sysUserService.userCount(sysUser)){
			result.put("success", false);
			result.put("msg", "用户已存在，请勿重复添加");
		} else {
			this.sysUserService.insert(sysUser);
		}
		
		//1表示该用户为部门负责人
		if("1".equals(sysUser.getBmfzr())){
			SysBmfzr bmfzr = new SysBmfzr();
			SysUser user = sysUserService.get(sysUser);
			String unitName = sysUserService.queryOrgNameByOrgCode(user.getParentOrgCode());
			bmfzr.setBumencode(user.getOrgCode());
			bmfzr.setBumenname(user.getOrgName());
			bmfzr.setLeadercode(user.getLoginId());
			bmfzr.setLeadername(user.getName());
			bmfzr.setUnitcode(user.getParentOrgCode());
			bmfzr.setUnitname(unitName);
			if(sysUserService.findBmfzr(bmfzr)== null){
				sysUserService.insertBmfzr(bmfzr);
			}
		}
		
		return result;
	}
	
	/**
	 * 修改
	 * @param sysUser SysUser 
	 * @param model Model
	 * @return Page<SysUser>
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String,Object> update(SysUser sysUser, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", "true");
		
		if(sysUserService.userCount(sysUser)){
			result.put("success", false);
			result.put("msg", "用户已存在，请勿重复添加");
		} else {
			this.sysUserService.update(sysUser);
		}
		
		//1表示该用户为部门负责人
		if("1".equals(sysUser.getBmfzr())){
			SysBmfzr bmfzr = new SysBmfzr();
			SysUser user = sysUserService.get(sysUser);
			String unitName = sysUserService.queryOrgNameByOrgCode(user.getParentOrgCode());
			bmfzr.setBumencode(user.getOrgCode());
			bmfzr.setBumenname(user.getOrgName());
			bmfzr.setLeadercode(user.getLoginId());
			bmfzr.setLeadername(user.getName());
			bmfzr.setUnitcode(user.getParentOrgCode());
			bmfzr.setUnitname(unitName);
			if(sysUserService.findBmfzr(bmfzr)== null){
				sysUserService.insertBmfzr(bmfzr);
			}
			
		}
		
		return result;
	}
	
	/**
	 * 删除
	 * @param sysUser SysUser 
	 * @param redirectAttributes RedirectAttributes
	 * @return String
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(SysUser sysUser, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysUserService.delete(sysUser);
		
		this.sysUserService.deleteBmfzr(sysUser.getLoginId());
		
		return map;
	}
	
	/**
	 * 查询出用户未有拥有的角色
	 * @param sysUserRole SysUserRole 
	 * @return Page<SysRole>
	 */
	@RequiresPermissions("sys:sysUser:view")
	@RequestMapping(value = {"queryNotGrantRoleById"})
	@ResponseBody
	public Page<SysRole> queryNotGrantRoleById(SysUserRole sysUserRole) {
		return this.sysRoleService.queryNotGrantRoleById(sysUserRole); 
	} 
	
	/**
	 * 查询出用户已有拥有的角色
	 * @param sysUserRole SysUserRole 
	 * @return Page<SysRole>
	 */
	@RequiresPermissions("sys:sysUser:view")
	@RequestMapping(value = {"queryGrantRoleById"})
	@ResponseBody
	public List<SysRole> queryGrantRoleById(SysUserRole sysUserRole) {
		List<SysRole> list = this.sysRoleService.queryGrantRoleById(sysUserRole);
		return list; 
	}
	
	/**
	 * 重置用户密码
	 * @param sysUser SysUser 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"resetPwd"})
	@ResponseBody
	public  Map<String,Object> resetPwd(SysUser sysUser){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysUserService.updatePasswordById(sysUser);
		
		return map;
	}
	
	/**
	 * 添加用户角色
	 * @param sysUser SysUserRole 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = {"addUserRoles"})
	@ResponseBody
	public  Map<String,Object> addUserRoles(SysUserRole sysUser){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysUserService.addUserRole(sysUser);
		
		return map;
	}
	
	/**
	 * 移除用户角色
	 * @param sysUser SysUserRole 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = {"removeUserRoles"})
	@ResponseBody
	public  Map<String,Object> removeUserRoles(SysUserRole sysUser){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysUserService.deleteUserRole(sysUser);
		
		return map;
	}
	
	/**
	 * 查询出用户已有拥有的授权机构
	 * @param sysOrgUser SysOrgUser 
	 * @return Page<SysRole>
	 */
	@RequiresPermissions("sys:sysUser:view")
	@RequestMapping(value = {"queryGrantOrgById"})
	@ResponseBody
	public List<SysOrg> queryGrantOrgById(SysOrgUser sysOrgUser) {
		if(StringUtils.isEmpty(sysOrgUser.getUserId())){
			return new ArrayList<SysOrg>();
		}
		return this.sysUserService.queryGrantOrgById(sysOrgUser); 
	}
	
	/**
	 * 添加用户授权机构
	 * @param sysOrgUser SysOrgUser 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = {"addGrantOrg"})
	@ResponseBody
	public  Map<String,Object> addGrantOrg(SysOrgUser sysOrgUser){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysUserService.addOrgUser(sysOrgUser);
		
		return map;
	}
	
	/**
	 * 删除用户授权机构
	 * @param sysOrgUser SysOrgUser 
	 * @return Map<String,Object>
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = {"removeGrantOrg"})
	@ResponseBody
	public  Map<String,Object> removeGrantOrg(SysOrgUser sysOrgUser){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		this.sysUserService.deleteOrgUser(sysOrgUser);
		
		return map;
	}
	
	/**
	 * 检查登录账号是否已经存
	 * @param sysUser SysUser
	 * @return boolean true表示存在
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = {"checkExsit4LoginId"})
	@ResponseBody
	public boolean checkExsit4LoginId(SysUser sysUser){
		SysUser entry = new SysUser();
		entry.setLoginId(sysUser.getLoginId());
		entry.setOrgCode(sysUser.getOrgCode());
		entry.setOrgId(sysUser.getOrgId());
		
		List<SysUser> list = this.sysUserService.findList(entry);
		return !list.isEmpty();
	}
	
	/**
	 * 下载导入数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = "download")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户信息导入模板.xlsx";
			List<SysUser> list = Lists.newArrayList();

			SysUser entity = new SysUser();
			entity.setLoginId("10010");
			entity.setName("示例用户1");
			entity.setSex("1");
			entity.setMobileTel("13000000000");
			entity.setEmail("00@163.com");
			entity.setOrgCode("24401000000");
			entity.setFullOrgNames("广东地税广州市局");
			entity.setShowOrder("1");
			entity.setTel("1000000");
			entity.setRemarks("备注");
			list.add(entity);
			SysUser entityb = new SysUser();
			entityb.setLoginId("10011");
			entityb.setName("示例用户2");
			entityb.setSex("0");
			entityb.setMobileTel("13000000001");
			entityb.setEmail("00@164.com");
			entityb.setOrgCode("24401000000");
			entityb.setFullOrgNames("广东地税|广州市局");
			entityb.setShowOrder("1");
			entityb.setTel("2000000");
			entityb.setRemarks("备注1");
			list.add(entityb);
			new ExportExcel("用户示例数据", SysUser.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/sysUser/list?repage";

	}

	/**
	 * 导入数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:sysUser:edit")
	@RequestMapping(value = "import",  produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String importFile(@RequestParam MultipartFile file) {
		String msg = "";
		StringBuilder sb = new StringBuilder();
		
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			//创建导入Excel对象
			ImportExcel ei = new ImportExcel(file, 1, 0);
			//获取传入Excel文件的数据，根据传入参数类型，自动转换为对象
			List<SysUser> list = ei.getDataList(SysUser.class);
			//遍历数据，保存数据
			for (SysUser sysUser : list) {
				try {
					SysOrg entity = new SysOrg();
					entity.setOrgCode(sysUser.getOrgCode());
					SysOrg sysOrg = sysOrgService.get(entity); //根据组织代码查询机构ID
					
					sysUser.setOrgId(sysOrg.getId());
					sysUser.setFullOrgNames(sysOrg.getFullOrgNames());
					
					if(sysUserService.userCount(sysUser)){
						failureMsg.append("<br/>登录账号:" + sysUser.getLoginId() + " 已存在; ");
						failureNum++;
					} else {
						sysUserService.insert(sysUser);
						successNum++;
					}
				} catch (Exception ex) {
					logger.debug(ex.getMessage());
					failureMsg.append("<br/>登录账号:" + sysUser.getLoginId() + " 导入失败; ");
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条数据，导入信息如下：");
			}
			String[] messages ={"已成功导入 " + successNum + " 条数据" + failureMsg};
			
			for (String message : messages){
				sb.append(message).append(messages.length>1?"<br/>":"");
			}
			msg = sb.toString();
		} catch (Exception e) {
			String[] messages ={"导入数据失败！失败信息：" + e.getMessage()};
			for (String message : messages){
				sb.append(message).append(messages.length>1?"<br/>":"");
			}
			msg = sb.toString();
		}
		return msg;
	}


}
