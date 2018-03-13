package com.javenluo.ranger.common.web;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.javenluo.ranger.common.utils.DateUtils;
import com.javenluo.ranger.sys.entity.SysRole;
import com.javenluo.ranger.sys.utils.UserUtils;


/**
 * 控制器支持类
 * @author gulong
 * @version 2013-3-23
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Log logger = LogFactory.getLog(getClass());
	
	@Value("${fbidp.top.orgCode}")
	public String topOrgCode;
	
	@Value("${fbidp.top.orgId}")
	public String topOrgId;

	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${fbidp.tmp.dir}")
	protected String tmpDir;
	
	/**
	 * 流程租户ID
	 */
	@Value("${fbidp.workflow.bpmTenandId}")
	protected String bpmTenandId;
	
	/**
	 * 流程租户ID
	 */
	@Value("${fbidp.workflow.bpmManagerRoleId}")
	protected String bpmManagerRoleId;
	
	/**
	 * 获取工作流程租户ID，处理逻辑：<br>
	 * 首先，判断当前登录用户是否有包含“工作流管理员”角色，如果有则取其用户ID，否则取系统默认的租户ID
	 * @return String
	 */
	public String getBpmTenandId(){
		boolean finded = false;
		List<SysRole> roleList = UserUtils.getUser().getRoleList();
		for (SysRole sysRole : roleList) {
			if(StringUtils.equals(bpmManagerRoleId, sysRole.getId())){
				finded = true;
				break;
			}
		}
		if(finded){
			return UserUtils.getUser().getId();
		}
		
		return this.bpmTenandId;
	}
	
	
	/**
	 * 添加Model消息
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}
	
	@ExceptionHandler
    public String exp(HttpServletRequest request, Exception ex) {
		logger.error("", ex);
        request.setAttribute("ex", ex);
        return "ex";
    }
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object){
		return renderString(response, JSONObject.toJSONString(object),"application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @param type
	 * @return
	 */
	protected String renderString(HttpServletResponse response,String string , String type){
		try {
		response.reset();
		response.setContentType(type);
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setHeader("Cache-Control", "no-cache");
		
		response.getWriter().print(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

}