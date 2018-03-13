/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import com.javenluo.ranger.common.persistence.DataEntity;
import com.javenluo.ranger.common.utils.excel.annotation.ExcelField;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(FBIDP_SYS_WBXT)的实体类
 * </pre>
 * @author mmr mmr@loafish.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysWbxt extends DataEntity<SysWbxt>{

	private static final long serialVersionUID = 1L;
	
	/** 系统编码 */
	private String xtbm;  //系统编码
	/** 系统名称 */
	private String xtmc;  //系统名称
	/** 所属机构 */
	private String orgId;  //所属机构
	/** 系统网址 */
	private String xtwz;  //系统网址
	/** 系统负责人 */
	private String xtfzr;  //系统负责人
	/** 负责人联系电话 */
	private String fxrlxdh;  //负责人联系电话
	/** 系统密码 */
	private String xtmm;  //系统密码
	
	private String orgName;
	
	private String fullOrgNames;
	
	/** 待办接口 */
	private String dbjk;  //待办接口
	
	private String jkzh; //接口账号
	
	private String jkmm; //接口密码
	
	private String tenantId;

	public SysWbxt() {

	}

	public SysWbxt(String xtbm) {
		this.xtbm = xtbm;
	}
	
	/**
	 * 获取外部系统编码
	 * @return
	 */
	@ExcelField(title="系统编码", type=1, align=1, sort=1)
	public String getXtbm(){
		return this.xtbm;
	}

	/**
	 * 获取外部系统名称
	 * @return
	 */
	@ExcelField(title="系统名称", type=1, align=1, sort=2)
	public String getXtmc(){
		return this.xtmc;
	}

	/**
	 * 获取外部系统所属机构
	 * @return
	 */
	@ExcelField(title="所属机构", type=1, align=1, sort=3)
	public String getFullOrgNames(){
		return this.fullOrgNames;
	}

	/**
	 * 获取外部系统网址
	 * @return
	 */
	@ExcelField(title="系统网址", type=1, align=1, sort=4)
	public String getXtwz(){
		return this.xtwz;
	}

	/**
	 * 获取外部系统负责人
	 * @return
	 */
	@ExcelField(title="负责人", type=1, align=1, sort=5)
	public String getXtfzr(){
		return this.xtfzr;
	}

	/**
	 * 获取外部系统负责人联系电话
	 * @return
	 */
	@ExcelField(title="联系电话", type=1, align=1, sort=6)
	public String getFxrlxdh(){
		return this.fxrlxdh;
	}

}
