
package com.javenluo.ranger.sys.interceptor;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.javenluo.ranger.common.service.BaseService;
import com.javenluo.ranger.common.utils.DateUtils;
import com.javenluo.ranger.common.utils.RequestUtil;
import com.javenluo.ranger.sys.utils.LogUtils;


/**
 * 日志拦截器
 * @author javenluo
 * @version 2014-8-19
 */
public class LogInterceptor extends BaseService implements HandlerInterceptor {

	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("ThreadLocal StartTime");
	
	/**
	 * 该方法在进入Handler方法执行之前执行此方法
	 * 应用场景：如身份认证，身份授权
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		if (log.isInfoEnabled()){
			long beginTime = System.currentTimeMillis();//1、开始时间  
	        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）  
	        log.info(String.format("开始计时: %s  URI: %s", new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime), request.getRequestURI()));
		} 
		
		return true;
	}

	/**
	 * 该方法在进入Handler方法之后，返回ModelAndView之前执行
	 * 应用场景从modelAndView出发，将公用模型数据（如菜单导航）在这里传到视图，也可以在这里统一制定视图
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null){
			log.info("ViewName: " + modelAndView.getViewName());
		}else{
			if(!RequestUtil.isAjaxRequest(request)){
				NoHandlerFoundException ex = new NoHandlerFoundException(request.getMethod(),request.getRequestURI(),new HttpHeaders());
			}
		}
	}

	/**
	 * 该方法在handler方法执行完之后执行
	 * 应用场景：统一日志处理，统一异常处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {

		// 保存日志
		LogUtils.saveLog(request, handler, ex, null);
		
		// 打印JVM信息。
		if (log.isInfoEnabled()){
			long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
			long endTime = System.currentTimeMillis(); 	//2、结束时间  
			log.info(String.format("计时结束：%s  耗时：%s  URI: %s  最大内存: %sm  已分配内存: %sm  已分配内存中的剩余空间: %sm  最大可用内存: %sm",
	        		new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.formatDateTime(endTime - beginTime),
					request.getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024, 
					(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024)); 
		}
		
	}

}
