package com.javenluo.ranger.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javenluo.ranger.common.web.BaseController;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.security.FormAuthenticationFilter;
import com.javenluo.ranger.sys.security.SystemAuthorizingRealm;
import com.javenluo.ranger.sys.service.SystemService;
import com.javenluo.ranger.sys.utils.UserUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foresee.fbidp.common.config.Global;
import com.foresee.fbidp.common.utils.CacheUtils;
import com.foresee.fbidp.common.utils.CookieUtils;
import com.foresee.fbidp.common.utils.IdGen;
import com.foresee.fbidp.common.utils.StringUtils;
import com.javenluo.ranger.sys.dao.ISysLoginDao;


/**
 * 登录Controller
 * @author gulong
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private ISysLoginDao loginDao;	

	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		
		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null /*&& !principal.isMobileLogin()*/
				&& StringUtils.equals(principal.getLoginName(), username)){ 
			return "redirect:" + adminPath;
		}else{
			UserUtils.getSubject().logout();
		}
		
		return "modules/sys/sysLogin";
	}
	
	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null ){
			if(StringUtils.equals(principal.getLoginName(), username)){
				if(mobile){
					return renderString(response, principal);
				}
				return "redirect:" + adminPath;
			}else{
				UserUtils.getSubject().logout();
			}
		}
		
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		//model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		if(mobile){
			return renderString(response, model);
		}
		
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		return "modules/sys/sysIndex";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = new HashMap<String, Integer>();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
	/**
	 *  获取用户列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/userList")
	@ResponseBody
	public List<SysUser> userList(HttpServletRequest request, HttpServletResponse response, Model model){
		String loginName = request.getParameter("loginName");
		List<SysUser> userList = UserUtils.findByLoginName(loginName);
		
		return userList;
	}
	
	/**
	 * 验证用户名密码
	 * @param username  用户名
	 * @param password  密码
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/checkUsernamePwd")
	@ResponseBody
	public Map<String,Object> checkUsernamePwd(String username,String password){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		
		try{
			//TODO:1,查询用户登录信息
			SysUser user = loginDao.getByLoginName(new SysUser(null, username));
			if(user==null){
				throw new RuntimeException("用户或密码错误, 请重试.");
			}
			
			//TODO：2,校验密码
			boolean flag = SystemService.validatePassword(password,user.getPasswd());
			if(!flag){
				throw new RuntimeException("用户或密码错误, 请重试.");
			}
			
			//TODO:3,查询用户信息
			List<SysUser> userList = UserUtils.findByLoginName(username);
			if(userList==null){
				throw new RuntimeException("用户或密码错误, 请重试.");
			}
			result.put("userList", userList);
		}catch(Exception ex){
			logger.error(ex.getMessage());
			result.put("success", false);
			result.put("message", ex.getMessage());
		}
		
		return result;
	}
	
}
