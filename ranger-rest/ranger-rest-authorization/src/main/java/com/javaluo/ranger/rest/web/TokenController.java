package com.javaluo.ranger.rest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaluo.ranger.rest.authorization.annotation.Authorization;
import com.javaluo.ranger.rest.authorization.annotation.CurrentUser;
import com.javaluo.ranger.rest.authorization.manager.TokenManager;
import com.javaluo.ranger.rest.entity.ResponeResult;
import com.javenluo.ranger.common.utils.IdGen;
import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.service.SystemService;



/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 * @author gulong 
 * @version 2017-10-16
 */
@RestController
@RequestMapping("${adminPath}/rest/security")
public class TokenController {
	
	@Autowired
    private TokenManager tokenManager;
	@Autowired
	private SystemService systemService ;

	/**
	 * 调用REST接口前的登录方法
	 * @param username String
	 * @return ResponseEntity<ResponeResult>
	 */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseEntity<ResponeResult> createToken(@RequestParam String username, @RequestParam String password ) {
    	//1:验证用户名和密码
    	SysUser user = systemService.getUserByLoginName(username);
    	if(user==null){
    		ResponeResult result = new ResponeResult(ResponeResult.FAIL,"用户名不正确",null);
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    	}
    	
    	boolean flag = systemService.validatePassword(password , user.getPasswd());
    	if(flag){
    		String token = IdGen.uuid();
    		
            tokenManager.createRelationship(username, token);
	            
            ResponeResult result = new ResponeResult();
            result.setCode(result.OK);
            result.setToken(token);
            
            return new ResponseEntity(result, HttpStatus.OK);
    	}else{
    		ResponeResult result = new ResponeResult(ResponeResult.FAIL,"用户名或密码不正确",null);
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    	}
    }

    /**
     * 注销REST的调用
     * @param username String
     * @return ResponseEntity<ResponeResult>
     */
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResponeResult> logout(@CurrentUser SysUser user) {
        tokenManager.delRelationshipByKey(user.getLoginId());
        return new ResponseEntity(new ResponeResult(ResponeResult.OK,null,null), HttpStatus.OK);
    }

}
