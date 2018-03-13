/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import com.javenluo.ranger.common.persistence.DataEntity;
import com.javenluo.ranger.common.utils.excel.annotation.ExcelField;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(FBIDP_BPM_BMFZR)的实体类
 * </pre>
 * @author xxx xxx@foresee.com.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysBmfzr extends DataEntity<SysBmfzr>{

	private static final long serialVersionUID = 1L;
	
	/** 所属单位 */
	private String unitname;  //所属单位
	/** 单位代码 */
	private String unitcode;  //单位代码
	/** 部门名称 */
	private String bumenname;  //部门名称
	/** 部门代码 */
	private String bumencode;  //部门代码
	/** 部门负责人税务代码 */
	private String leadercode;  //部门负责人税务代码
	/** 部门负责人 */
	private String leadername;  //部门负责人
	/** 租户ID */
	private String tenantId;  //租户ID

	/**
	 * 获取所属单位
	 * @return
	 */
	@ExcelField(title="所属单位", type=1, align=1, sort=1)
	public String getUnitname(){
		return this.unitname;
	}

	/**
	 * 获取单位代码
	 * @return
	 */
	@ExcelField(title="单位代码", type=1, align=1, sort=2)
	public String getUnitcode(){
		return this.unitcode;
	}

	/**
	 * 获取部门名称
	 * @return
	 */
	@ExcelField(title="部门名称", type=1, align=1, sort=3)
	public String getBumenname(){
		return this.bumenname;
	}

	/**
	 * 获取部门代码
	 * @return
	 */
	@ExcelField(title="部门代码", type=1, align=1, sort=4)
	public String getBumencode(){
		return this.bumencode;
	}

	/**
	 * 获取部门负责人税务代码
	 * @return
	 */
	@ExcelField(title="部门负责人税务代码", type=1, align=1, sort=5)
	public String getLeadercode(){
		return this.leadercode;
	}

	/**
	 * 获取部门负责人
	 * @return
	 */
	@ExcelField(title="部门负责人", type=1, align=1, sort=6)
	public String getLeadername(){
		return this.leadername;
	}

	/**
	 * 获取租户id
	 * @return
	 */
	@ExcelField(title="租户ID", type=1, align=1, sort=7)
	public String getTenantId(){
		return this.tenantId;
	}

}
