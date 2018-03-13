package com.javenluo.ranger.common.persistence;

import java.io.Serializable;
import java.util.Date;

import com.javenluo.ranger.sys.entity.SysUser;
import com.javenluo.ranger.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;

import com.javenluo.ranger.common.config.Global;
import com.javenluo.ranger.common.utils.IdGen;

import lombok.Data;

/**
 * 数据Entity类
 * @author gulong
 * @version 2014-05-16
 */
@Data
public abstract class DataEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected String ids;
	protected String remarks;	// 备注
	protected String createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected String updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	protected String enableFlag;   //启用状态（1、启用，0、禁用）
	protected Long start;
	protected Long limit;
	protected Long count;
	
	protected Long page;
	protected Long rows;
	
	private String dbName;
	
	/**
	 * 删除标记（0：正常；1：删除；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	/**
	 * 激活标记（0：禁用；1：有效；）
	 */
	public static final String ENABLE_FLAG_NORMAL = "1";
	public static final String ENABLE_FLAG_DISABLED = "0";
	
	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
		this.enableFlag = ENABLE_FLAG_NORMAL;
		this.dbName = Global.getConfig("jdbc.type");
	}
	
	/***
	 * 重新初始默认值
	 */
	public void preInitDataEntity(){
		this.delFlag = DEL_FLAG_NORMAL;
		this.enableFlag = ENABLE_FLAG_NORMAL;
		this.dbName = Global.getConfig("jdbc.type");
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert(){
		setId(IdGen.uuid());
		SysUser user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user.getId();
			this.createBy = user.getId();
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate(){
		SysUser user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user.getId();
		}
		this.updateDate = new Date();
	}
	
}
