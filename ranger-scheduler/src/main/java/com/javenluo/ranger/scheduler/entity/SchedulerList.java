package com.javenluo.ranger.scheduler.entity;

import com.javenluo.ranger.common.persistence.DataEntity;
import com.javenluo.ranger.common.utils.excel.annotation.ExcelField;

import lombok.Data;

/**
 * 计划任务实体类
 * @author gulong
 * @version 2017-10-16
 */
@Data
public class SchedulerList extends DataEntity<SchedulerList> {
	private static final long serialVersionUID = 1L;
	/** 任务名称 */
	private String rwmc;
	/** 时间表达式 */
	private String sjbds;
	/** 作业类名 */
	private String zylm;
	/** 状态 */	
	private String zt;
	/** 下次执行时间(时间戳) */
	private Long xczxsj;
	/** 下次执行时间(yyyy-MM-dd HH:mm:ss) */
	private String xczxsjStr;
	/** 上次执行时间(时间戳) */
	private Long sczxsj;
	/** 上次执行时间(yyyy-MM-dd HH:mm:ss) */
	private String sczxsjStr;
	/** 任务描述 */
	private String rwms;
	
	@ExcelField(title="任务名称", type=0, align=1, sort=1)
	public String getRwmc() {
		return this.rwmc;
	}

	@ExcelField(title="时间表达式", type=0, align=1, sort=2)
	public String getSjbds() {
		return this.sjbds;
	}

	@ExcelField(title="作业类名", type=0, align=1, sort=3)
	public String getZylm() {
		return this.zylm;
	}

	@ExcelField(title="状态", type=0, align=1, sort=4)
	public String getZt() {
		return this.zt;
	}

	@ExcelField(title="上次执行时间", type=0, align=1, sort=5) 
	public String getSczxsjStr() {
		return this.sczxsjStr;
	}
	
	@ExcelField(title="下次执行时间", type=0, align=1, sort=6)
	public String getXczxsjStr() {
		return this.xczxsjStr;
	}

	@ExcelField(title="任务描述", type=0, align=1, sort=7)
	public String getRwms() {
		return this.rwms;
	}
	
}
