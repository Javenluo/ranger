package com.javenluo.ranger.common.persistence;

import java.util.Properties;

import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.utils.UserUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Intercepts({  
    @Signature(type = Executor.class, method = "update", args = {  
            MappedStatement.class, Object.class }),  
    @Signature(type = Executor.class, method = "query", args = {  
            MappedStatement.class, Object.class, RowBounds.class,  
            ResultHandler.class }) 
}) 
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Component
@Slf4j
public class AutoFillInterceptor implements Interceptor {
	
	private Properties properties;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();  
  
        final Object[] args = invocation.getArgs();  
        MappedStatement mappedStatement = (MappedStatement) args[0];  
        
        Object parameter = invocation.getArgs()[1];  
        if(parameter==null || !(parameter instanceof DataEntity))  {
        	 return invocation.proceed();  
        }
        
        SysUser u = UserUtils.getUser();
        if(u==null){
        	return invocation.proceed();  
        }
        
        SqlCommandType commandType = mappedStatement.getSqlCommandType();
        
        DataEntity d = ((DataEntity)parameter);
        if(StringUtils.isEmpty(d.getEnableFlag())){
        	//d.setQyId(u.getQyId()); 
        }
        
        
        
        log.debug("----->MyInterceptor拦截方法【"+methodName+"】成功写入QyId【】");
        
        
        return invocation.proceed();  
	}
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);  
	}

	@Override
	public void setProperties(Properties properties) {
		 this.properties = properties;  
	}

}
