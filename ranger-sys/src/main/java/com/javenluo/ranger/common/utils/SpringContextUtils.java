package com.javenluo.ranger.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.javenluo.ranger.common.support.TableExportReturnValueHandler;



public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext context;
	
    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringContextUtils.context = context;
        
        RequestMappingHandlerAdapter rha = this.context.getBean(RequestMappingHandlerAdapter.class);
        List<HandlerMethodReturnValueHandler> handlers = rha.getReturnValueHandlers();
        
        List<HandlerMethodReturnValueHandler> list = new ArrayList<HandlerMethodReturnValueHandler>();
        for (HandlerMethodReturnValueHandler handler : handlers) {
			if(handler.getClass() == RequestResponseBodyMethodProcessor.class){
				RequestResponseBodyMethodProcessor rr = (RequestResponseBodyMethodProcessor)handler;
				
				TableExportReturnValueHandler t = new TableExportReturnValueHandler(rr);
				
				list.add(t);
			}
			
			list.add(handler);
		}
        
        rha.setReturnValueHandlers(list);
    }
    
    public static ApplicationContext getContext(){
        return context;
    }
	
}
