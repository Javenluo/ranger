/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import java.util.ArrayList;
import java.util.List;

import com.javenluo.ranger.common.persistence.DataEntity;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(fbidp_sys_org)的实体类
 * </pre>
 * @author gulong 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
 @Data
public class SysOrg extends DataEntity<SysOrg>{
	private static final long serialVersionUID = 1L;
	
	/** 有业务意义的机构编号 */
	private String orgCode;
	/** 机构名称 */
	private String orgName;
	/** 机构简称 */
	private String orgShortName;
	/** 父机构ID */
	private String parentOrgId;
	/** 父机构CODE */
	private String parentOrgCode;
	/** 父机构名称 */
	private String parentOrgName;
	/** 全路径机构ID */
	private String fullOrgIds;
	/** 全路径机构CODE */
	private String fullOrgCodes;
	/** 全路径机构名称 */
	private String fullOrgNames;
	
	private String orgType;
	private String leaderId;
	private String leaderName;
	private String phone;
	private String address;
	private int showOrder;
	private String status;
	private String userId;
	boolean withSelf;
	private String swbmbj;
	
	private String oldOrgCode;
	private String oldOrgName;
	private String oldOrgShortName;
	private String oldFullOrgIds;
	private String oldFullOrgCodes;
	private String oldFullOrgNames;
	
	private List<String> fullOrgCodeList = new ArrayList<String>();
	
	private List<SysOrg> children = new ArrayList<SysOrg>();
	
	private String state;
	
	private int childNum;
	
	private String jdjb; //节点级别

	private String orgAttribute;
	
	private String xzqhjc;//行政区划级次
	
}
