package com.javenluo.ranger.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.foresee.fbidp.common.utils.RequestUtil;
import com.foresee.fbidp.common.utils.StringUtils;
import com.javenluo.ranger.sys.security.SystemAuthorizingRealm.Principal;
import com.javenluo.ranger.sys.utils.UserUtils;



/**
 * 表单验证（包含验证码）过滤类
 * @author gulong
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM; //验证码值
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;//登陆手机
	private String messageParam = DEFAULT_MESSAGE_PARAM;//信息值

	/**
	 * 创建token
	 */
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		String captcha = getCaptcha(request);
		String userId = getUserId(request);
		boolean mobile = isMobileLogin(request);
		
		return new UsernamePasswordToken(userId,username, password.toCharArray(), rememberMe, host, captcha, mobile);
	}
	
	/**
	 * 当访问拒绝时是否已经处理
	 */
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
            	HttpServletRequest httpRequest = WebUtils.toHttp(request);
                
                if (RequestUtil.isAjaxRequest(httpRequest)) {
                    HttpServletResponse httpServletResponse = WebUtils.toHttp(response); 
                    httpServletResponse.setHeader("sessionstatus", "timeout");//在响应头设置session状态
                    httpServletResponse.sendError(401);
                    return false;
                }
                // 放行 allow them to see the login page ;)
                return true;
            }
        } else {
            HttpServletRequest httpRequest = WebUtils.toHttp(request);
            
            if (RequestUtil.isAjaxRequest(httpRequest)) {
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response); 
                httpServletResponse.setHeader("sessionstatus", "timeout");//在响应头设置session状态
                httpServletResponse.sendError(401);
                return false;
            } else {  
                saveRequestAndRedirectToLogin(request, response); 
            }  

            return false;
        }
    }
	
	/**
	 * 是否允许访问
	 */
	@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                //本次用户登陆账号
                String account = this.getUsername(request);

                Subject subject = this.getSubject(request, response);
                
                //之前登陆的用户
                Principal user = (Principal) subject.getPrincipal();
                //如果两次登陆的用户不一样，则先退出之前登陆的用户
                if (account != null && user != null && !account.equals(user.getLoginName())) {
                    subject.logout();
                }
            }
        }

        return super.isAccessAllowed(request, response, mappedValue);
    }

	public String getCaptchaParam() {
		return captchaParam;
	}

	/**
	 * 获取验证码
	 * @param request
	 * @return
	 */
	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
	
	/**
	 * 获取用户id
	 * @param request
	 * @return
	 */
	protected String getUserId(ServletRequest request) {
		return WebUtils.getCleanParam(request, "userId");
	}

	/**
	 * 获取登录手机号码
	 * @return
	 */
	public String getMobileLoginParam() {
		return mobileLoginParam;
	}
	
	protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }
	
	/**
	 * 获取信息值
	 * @return
	 */
	public String getMessageParam() {
		return messageParam;
	}
	
	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	/**
	 * 成功跳转
	 */
	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		Principal p = UserUtils.getPrincipal();
		if (p != null && !p.isMobileLogin()) {
			WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
		} else {
			// super.issueSuccessRedirect(request, response);
			HttpServletResponse res = (HttpServletResponse) response;
			res.setContentType("application/json");
			res.setCharacterEncoding("utf-8");
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Access-Control-Allow-Methods", "GET,POST");
			res.setHeader("Cache-Control", "no-cache");

			res.getWriter().print(JSON.toJSONString(p));
		}

	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)){
			message = "用户或密码错误, 请重试.";
		}
		else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		}
		else{
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
	}
	
}