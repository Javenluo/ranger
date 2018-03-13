package com.javenluo.ranger.common.security.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javenluo.ranger.common.utils.StringUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;


/**
 * 自定义WEB会话管理类
 * @author gulong
 * @version 2014-7-20
 */
public class SessionManager extends DefaultWebSessionManager {

	/**
	 * 构造方法
	 */
	public SessionManager() {
		super();
	}
	
	/**
	 * 获取活动会话ID
	 * @param request 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @param response 根据登录者对象获取活动会话
	 * @return Serializable
	 */
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		// 如果参数中包含“__sid”参数，则使用此sid会话。 例如：http://localhost/project?__sid=xxx&__cookie=true
		String sid = request.getParameter("__sid");
		if (StringUtils.isNotBlank(sid)) {
			// 是否将sid保存到cookie，浏览器模式下使用此参数。
			if (WebUtils.isTrue(request, "__cookie")){
		        HttpServletRequest rq = (HttpServletRequest)request;
		        HttpServletResponse rs = (HttpServletResponse)response;
				Cookie template = getSessionIdCookie();
		        Cookie cookie = new SimpleCookie(template);
				cookie.setValue(sid); cookie.saveTo(rq, rs);
			}
			// 设置当前session状态
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.URL_SESSION_ID_SOURCE); // session来源与url
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        	return sid;
		}else{
			return super.getSessionId(request, response);
		}
	}
	
	/**
	 * 验证所有会话是否过期  
	 **/
	@Override
	public void validateSessions() {
		super.validateSessions();
	}
	
	/**
	 * 获取Sessions
	 * @param sessionKey 会话Key
	 **/
	protected Session retrieveSession(SessionKey sessionKey) {
		try{
			return super.retrieveSession(sessionKey);
		}catch (UnknownSessionException e) {
    		// 获取不到SESSION不抛出异常
			return null;
		}
	}

	/**
	 * 获取会话启动时间
	 * @param key 会话Key
	 **/
    public Date getStartTimestamp(SessionKey key) {
    	try{
    		return super.getStartTimestamp(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    /**
	 * 获取会话最后访问时间
	 * @param key 会话Key
	 **/
    public Date getLastAccessTime(SessionKey key) {
    	try{
    		return super.getLastAccessTime(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    /**
	 * 获取当前Session的过期时间
	 * @param key 会话Key
	 **/
    public long getTimeout(SessionKey key){
    	try{
    		return super.getTimeout(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return 0;
		}
    }
    
    /**
	 * 设置当前Session的过期时间
	 * @param key 会话Key
	 * @param maxIdleTimeInMillis 过期时间
	 **/
    public void setTimeout(SessionKey key, long maxIdleTimeInMillis) {
    	try{
    		super.setTimeout(key, maxIdleTimeInMillis);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
		}
    }

    /**
   	 * 更新会话最后访问时间
   	 * @param key 会话Key
   	 **/
    public void touch(SessionKey key) {
    	try{
	    	super.touch(key);
		}catch (InvalidSessionException e) {
			// 获取不到SESSION不抛出异常
		}
    }

    /**
   	 * 获取当前Subject的主机地址
   	 * @param key 会话Key
   	 **/
    public String getHost(SessionKey key) {
    	try{
    		return super.getHost(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    /**
   	 * 获取会话属性Keys
   	 * @param key 会话Key
   	 **/
    public Collection<Object> getAttributeKeys(SessionKey key) {
    	try{
    		return super.getAttributeKeys(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    /**
   	 * 获取会话属性
   	 * @param sessionKey 会话Key
   	 * @param attributeKey 属性Key
   	 **/
    public Object getAttribute(SessionKey sessionKey, Object attributeKey) {
    	try{
    		return super.getAttribute(sessionKey, attributeKey);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    /**
   	 * 设置会话属性
   	 * @param sessionKey 会话Key
   	 * @param attributeKey 属性Key
   	 * @param value value值
   	 **/
    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) {
    	try{
    		super.setAttribute(sessionKey, attributeKey, value);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
		}
    }

    /**
   	 * 删除会话属性
   	 * @param sessionKey 会话Key
   	 * @param attributeKey 属性Key
   	 **/
    public Object removeAttribute(SessionKey sessionKey, Object attributeKey) {
    	try{
    		return super.removeAttribute(sessionKey, attributeKey);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    /**
	 * 销毁会话
	 * @param key 会话Key
	 **/
    public void stop(SessionKey key) {
    	try{
    		super.stop(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
		}
    }
    
    /**
	 * 校验会话是否有效
	 * @param key 会话Key
	 **/
    public void checkValid(SessionKey key) {
    	try{
    		super.checkValid(key);
		}catch (InvalidSessionException e) {
			// 获取不到SESSION不抛出异常
		}
    }
    
    /**
	 * 创建会话实例
	 * @param context session上下文
	 **/
    @Override
    protected Session doCreateSession(SessionContext context) {
    	try{
    		return super.doCreateSession(context);
		}catch (IllegalStateException e) {
			return null;
		}
    }

	/**
	 * 新会话实例
	 * @param context session上下文
	 **/
	@Override
	protected Session newSessionInstance(SessionContext context) {
		Session session = super.newSessionInstance(context);
		session.setTimeout(getGlobalSessionTimeout());
		return session;
	}

	/**
	 * 启动会话
	 * @param context session上下文
	 **/
    @Override
    public Session start(SessionContext context) {
    	try{
    		return super.start(context);
		}catch (NullPointerException e) {
			SimpleSession session = new SimpleSession();
			session.setId(0);
			return session;
		}
    }
}