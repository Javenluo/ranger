/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import com.javenluo.ranger.common.persistence.DataEntity;
import com.javenluo.ranger.common.utils.excel.annotation.ExcelField;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(fbidp_sys_ywgzsm)的实体类
 * </pre>
 * @author mmr mmr@loafish.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysYwgzsm extends DataEntity<SysYwgzsm>{

	private static final long serialVersionUID = 1L;
	
	/** 业务规则编码 */
	private String ywgzBm;  //业务规则编码
	/** 业务规则说明 */
	private String ywgzSm;  //业务规则说明
	/** 业务规则说明(富文本) */
	private String ywgzSmFwb;  // 业务规则说明(富文本)
	

	/**
	 * 获取外部系统业务规则编码
	 * @return
	 */
	@ExcelField(title="业务规则编码", type=1, align=1, sort=1)
	public String getYwgzBm(){
		return this.ywgzBm;
	}

	/**
	 * 获取外部系统业务规则说明
	 * @return
	 */
	@ExcelField(title="业务规则说明", type=1, align=1, sort=2)
	public String getYwgzSm(){
		return this.ywgzSm;
	}

	/**
	 * 获取外部系统业务规则说明（富文本）
	 * @return
	 */
	@ExcelField(title="备注", type=1, align=1, sort=3)
	public String getRemarks(){
		return this.remarks;
	}
}
