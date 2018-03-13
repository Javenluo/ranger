package com.javaluo.ranger.rest.authorization.resolvers;


import com.javaluo.ranger.rest.authorization.annotation.CurrentUser;
import com.javaluo.ranger.rest.authorization.interceptor.AuthorizationInterceptor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.foresee.fbidp.sys.entity.SysUser;
import com.foresee.fbidp.sys.service.SysUserService;

/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 * @author gulong 
 * @version 2017-10-16
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    //通过Key获得用户模型的实现类
    private SysUserService sysUserService;

    /**
     * 判断是否支持@CurrentUser
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是User并且有CurrentUser注解则支持
        return parameter.getParameterType().isAssignableFrom(SysUser.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class);
    }

    /**
     * 获取登陆用户
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出鉴权时存入的登录用户Id
        Object object = webRequest.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_KEY, RequestAttributes.SCOPE_REQUEST);
        if (object != null) {
            String key = String.valueOf(object);
            
            //从数据库中查询并返回
            SysUser entity = new SysUser();
    		entity.setLoginId(key);
    		SysUser userModel = sysUserService.get(entity);
            if (userModel != null) {
                return userModel;
            }
            
            //有key但是得不到用户，抛出异常
            throw new MissingServletRequestPartException(AuthorizationInterceptor.REQUEST_CURRENT_KEY);
        }
        //没有key就直接返回null
        return null;
    }
    
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}
