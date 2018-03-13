/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import java.util.ArrayList;
import java.util.List;

import com.javenluo.ranger.common.persistence.DataEntity;
import com.javenluo.ranger.common.utils.excel.annotation.ExcelField;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(fbidp_sys_role)的实体类
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysRole extends DataEntity<SysRole>{
	private static final long serialVersionUID = 1L;
	
	/** 有业务意义的角色编号 */
	private String code;  //有业务意义的角色编号
	/** 角色名称 */
	private String name;  //角色名称
	/** 组织机构ID */
	private String orgId;  //组织机构ID
	/** 显示顺序 */
	private Integer showOrder;  //显示顺序
	/** 角色的作用范围,0-全省可用,1-机关下可用 */
	private String scope;  //角色的作用范围,0-全省可用,1-机关下可用
	
	private String ids;
	
	private String orgCode;
	private String orgName;
	private String fullOrgCodes;
	private String fullOrgNames;
	private String userId;
	
	private String childNum;
	
	private SysUser user;
	
	private List<SysMenu> menuList = new ArrayList<SysMenu>();
	
	public SysRole(){
		
	}
	
	public SysRole(SysUser user){
		this.user = user;
	}
	
	/**
	 * 获取角色编码
	 * @return
	 */
	@ExcelField(title="角色编码", type=1, align=1, sort=1)
	public String getCode(){
		return this.code;
	}
	
	/**
	 * 获取角色名称
	 * @return
	 */
	@ExcelField(title="角色名称", type=1, align=1, sort=2)
	public String getName(){
		return this.name;
	}
	
	/**
	 * 获取角色所属机构
	 * @return
	 */
	@ExcelField(title="所属机构", type=1, align=1, sort=3)
	public String getFullOrgNames(){
		return this.fullOrgNames;
	}
	
	/**
	 * 获取角色是否有效
	 */
	@ExcelField(title="是否有效", type=1, align=2, sort=5,dictType="EnableFlag")
	public String getEnableFlag(){
		return this.enableFlag;
	}
	
	/**
	 * 获取角色显示顺序
	 * @return
	 */
	@ExcelField(title="显示顺序", type=1, align=1, sort=6)
	public Integer getShowOrder(){
		return this.showOrder;
	}
	
	/**
	 * 获取角色备注
	 */
	@ExcelField(title="备注", type=1, align=1, sort=20)
	public String getRemarks(){
		return this.remarks;
	}

}
