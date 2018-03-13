/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import java.util.Date;
import java.util.Map;

import com.javenluo.ranger.common.utils.excel.annotation.ExcelField;
import org.springframework.web.util.HtmlUtils;

import com.javenluo.ranger.common.persistence.DataEntity;
import com.foresee.fbidp.common.utils.StringUtils;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(fbidp_sys_log)的实体类
 * </pre>
 * @author wujinghui wujinghui@foresee.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysLog extends DataEntity<SysLog>{

	private static final long serialVersionUID = 1L;
	
	/** 日志类型 */
	private String type;  //日志类型
	/** 日志标题 */
	private String title;  //日志标题
	/** 操作IP地址 */
	private String remoteAddr;  //操作IP地址
	/** 用户代理 */
	private String userAgent;  //用户代理
	/** 请求URI */
	private String requestUri;  //请求URI
	/** 操作方式 */
	private String method;  //操作方式
	/** 操作提交的数据 */
	private String params;  //操作提交的数据
	/** 异常信息 */
	private String exception;  //异常信息
	
	private Date beginDate;		// 开始日期
	private Date endDate;		// 结束日期
	private String createByName;	//创建者名称
	private String orgName;		//所属税务机关
	
	private String createStr; //创建时间
	
	// 日志类型（1：接入日志；2：错误日志）
	public static final String TYPE_ACCESS = "1";
	public static final String TYPE_EXCEPTION = "2";

	/**
	 * 获取系统日志标题
	 * @return
	 */
	@ExcelField(title="日志标题", type=1, align=1, sort=1)
	public String getTitle(){
		return this.title;
	}
	
	/**
	 * 获取系统日志所属税务机关
	 * @return
	 */
	@ExcelField(title="所属税务机关", type=1, align=1, sort=2)
	public String getOrgName(){
		return this.orgName;
	}
	
	/**
	 * 获取系统日志请求URI
	 * @return
	 */
	@ExcelField(title="请求URI", type=1, align=1, sort=3)
	public String getRequestUri(){
		return this.requestUri;
	}

	/**
	 * 获取系统日志操作方式
	 * @return
	 */
	@ExcelField(title="操作方式", type=1, align=1, sort=4)
	public String getMethod(){
		return this.method;
	}

	/**
	 * 获取系统日志操作ip地址
	 * @return
	 */
	@ExcelField(title="操作IP地址", type=1, align=1, sort=5)
	public String getRemoteAddr(){
		return this.remoteAddr;
	}

	/**
	 * 获取系统日志异常信息
	 * @return
	 */
	@ExcelField(title="异常信息", type=1, align=1, sort=6)
	public String getException(){
		exception = HtmlUtils.htmlEscape(exception);
		return exception;
	}
	
	/**
	 * 获取系统日志操作人
	 * @return
	 */
	@ExcelField(title="操作用户", type=1, align=1, sort=7)
	public String getCreateByName(){
		return this.createByName;
	}
	
	/**
	 * 获取系统日志操作日期
	 * @return
	 */
	@ExcelField(title="操作日期", type=1, align=1, sort=8)
	public String getCreateStr(){
		return this.createStr;
	}
	
	/**
	 * 设置请求参数
	 * @param paramMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParams(Map paramMap){
		if (paramMap == null){
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
			params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
		}
		this.params = params.toString();
	}
}
