/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import com.javenluo.ranger.common.persistence.DataEntity;
import com.javenluo.ranger.common.utils.excel.annotation.ExcelField;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(FBIDP_SYS_NOTICE)的实体类
 * </pre>
 * @author xxx xxx@foresee.com.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysNotice extends DataEntity<SysNotice>{

	private static final long serialVersionUID = 1L;
	
	/** 公告标题 */
	private String noticeTitle;  //公告标题
	/** 公告类型,0-系统公告,1-系统通知,2-消息 */
	private String noticeType;  //公告类型,0-系统公告,1-系统通知,2-消息
	/** 该公告有效的截止日期 */
	private java.util.Date validDate;  //该公告有效的截止日期
	/** 公告是否有效,1-有效,2,待清理 */
	private String validFlag;  //公告是否有效,1-有效,2,待清理
	/** 公告内容 */
	private String content;  //公告内容
	/** 要通知的ID，带@的是机构ID，否则是人员ID */
	private String contactId;  //要通知的ID，带@的是机ID，否则是人员ID
	/** 是否查看（0：未查看，1：已查看） */
	private String checked;  //是否查看（0：未查看，1：已查看）
	
	private String fqsjq;//发起时间起
	
	private String fqsjz;//发起时间止

	/**
	 * 获取系统公告标题
	 * @return
	 */
	@ExcelField(title="公告标题", type=1, align=1, sort=1)
	public String getNoticeTitle(){
		return this.noticeTitle;
	}

	/**
	 * 获取系统公告类型
	 * @return
	 */
	@ExcelField(title="公告类型,0-系统公告,1-系统通知,2-消息", type=1, align=1, sort=2)
	public String getNoticeType(){
		return this.noticeType;
	}

	/**
	 * 获取系统公告有效截止日期
	 * @return
	 */
	@ExcelField(title="该公告有效的截止日期", type=1, align=1, sort=3)
	public java.util.Date getValidDate(){
		return this.validDate;
	}

	/**
	 * 获取系统公告是否有效
	 * @return
	 */
	@ExcelField(title="公告是否有效,1-有效,2,待清理", type=1, align=1, sort=4)
	public String getValidFlag(){
		return this.validFlag;
	}

	/**
	 * 获取系统公告内容
	 * @return
	 */
	@ExcelField(title="公告内容", type=1, align=1, sort=5)
	public String getContent(){
		return this.content;
	}

	/**
	 * 获取系统公告需要通知的id
	 * @return
	 */
	@ExcelField(title="要通知的ID，带@的是机构ID，否则是人员ID", type=1, align=1, sort=6)
	public String getContactId(){
		return this.contactId;
	}

    /**
     * 获取系统公告是否查看
     * @return
     */
	@ExcelField(title="是否查看（0：未查看，1：已查看）", type=1, align=1, sort=8)
	public String getChecked(){
		return this.checked;
	}

}
