package com.javaluo.ranger.rest.entity;

import lombok.Data;

/**
 * 自定义返回结果
 * @author gulong 
 * @version 2017-10-16
 */
@Data
public class ResponeResult {
	
	public static String OK = "200"; //正常
	public static String FAIL = "400"; //正常
	
	 /**
     * 返回码
     */
    private String code;
    
    /**
     * 返回token
     */
    private String token;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private Object data;
    
    public ResponeResult(){
    	
    }
    
    public ResponeResult(String code,String message,Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    
    public ResponeResult(String code,String message) {
        this.code = code;
        this.message = message;
    }
}
