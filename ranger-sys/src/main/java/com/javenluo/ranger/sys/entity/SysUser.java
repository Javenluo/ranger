/**
 * Copyright &copy; 2012-2017 <a href="http://www.foresee.com">Foresee</a> All rights reserved.
 */
package com.javenluo.ranger.sys.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.javenluo.ranger.common.persistence.DataEntity;
import com.javenluo.ranger.common.utils.excel.annotation.ExcelField;

import lombok.Data;

/**
 * <pre>
 * 对应数据库表(Fbidp_sys_user)的实体类
 * </pre>
 * 
 * @author gulong
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Data
public class SysUser extends DataEntity<SysUser> {
	private static final long serialVersionUID = 1L;

	/** 用户姓名 */
	private String name; // 用户姓名
	/** 身份证号码 */
	private String idNumber; // 身份证号码
	/** 出生日期 */
	private String birthday; // 出生日期
	/** 性别，1男，0女 */
	private String sex; // 性别，1男，0女
	/** 电话 */
	private String tel; // 电话
	/** 手机 */
	private String mobileTel; // 手机
	/** 邮箱 */
	private String email; // 邮箱
	/** 登录用户名 */
	private String loginId; // 登录用户名
	/** 登录用户的密码 */
	private String passwd; // 登录用户的密码
	/** 上一次登录的时间 */
	private java.util.Date lastLoginTime; // 上一次登录的时间
	private String showOrder;
	private List<SysRole> roleList = new ArrayList<SysRole>();

	private List<String> roleIdList = new ArrayList<String>();

	private String oldLoginId;
	private String oldLoginName;// 原登录名

	private String oldLoginIp; // 上次登陆IP
	private Date oldLoginDate; // 上次登陆日期

	private String orgId;
	private String orgCode;
	private String orgName;
	private String parentOrgCode;

	private String fullOrgIds;
	private String fullOrgCodes;
	private String fullOrgNames;

	private String loginIp;
	private Date loginDate;

	private String ids;

	private List<String> orgIdList = new ArrayList<String>();
	private List<String> orgCodeList = new ArrayList<String>();
	private List<String> fullOrgIdList = new ArrayList<String>();
	private List<String> fullOrgCodeList = new ArrayList<String>();

	private String newPasswd;
	private String roleId; // 角色ID

	private String bmfzr;// 是否部门负责人，0-否，1-是

	public SysUser() {

	}

	public SysUser(String id) {
		this.setId(id);
	}

	public SysUser(String id, String loginName) {
		this.setId(id);
		this.setLoginId(loginName);
	}

	/**
	 * 是否是管理员
	 * @return
	 */
	public boolean isAdmin() {
		return isAdmin(this.getId());
	}

	/**
	 * 根据id判断是否是管理员
	 * @param id
	 * @return
	 */
	public static boolean isAdmin(String id) {
		return id != null && "1".equals(id);
	}

	/**
	 * 获取用户登录账号
	 * 
	 * @return
	 */
	@ExcelField(title = "登陆账号", type = 0, align = 1, sort = 1)
	public String getLoginId() {
		return this.loginId;
	}

	/**
	 * 获取用户名称
	 * @return
	 */
	@ExcelField(title = "用户名称", type = 0, align = 1, sort = 2)
	public String getName() {
		return this.name;
	}

	/**
	 * 获取用户性别
	 * @return
	 */
	@ExcelField(title = "性别", type = 0, align = 1, sort = 3, dictType = "Sex")
	public String getSex() {
		return this.sex;
	}

	/**
	 * 获取用户电话
	 * @return
	 */
	@ExcelField(title = "电话", type = 0, align = 1, sort = 4)
	public String getMobileTel() {
		return this.mobileTel;
	}

	/**
	 * 获取用户邮箱
	 * @return
	 */
	@ExcelField(title = "邮箱", type = 0, align = 1, sort = 5)
	public String getEmail() {
		return this.email;
	}

	/**
	 * 获取用户所属机构代码
	 * @return
	 */
	@ExcelField(title = "有效", type = 0, align = 1, sort = 6, dictType = "EnableFlag")
	public String getEnableFlag() {
		return this.enableFlag;
	}

	/**
	 * 获取用户所属机构代码
	 * @return
	 */
	@ExcelField(title = "所属机构代码", type = 0, align = 1, sort = 7)
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * 获取用户所属机构名称
	 * @return
	 */
	@ExcelField(title = "所属机构名称", type = 0, align = 1, sort = 8)
	public String getFullOrgNames() {
		return this.fullOrgNames;
	}

	/**
	 * 获取用户排列顺序
	 * @return
	 */
	@ExcelField(title = "排列顺序", type = 0, align = 2, sort = 9)
	public String getShowOrder() {
		return this.showOrder;
	}

	/**
	 * 获取用户办公电话
	 * @return
	 */
	@ExcelField(title = "办公电话", type = 0, align = 1, sort = 10)
	public String getTel() {
		return this.tel;
	}

	/**
	 * 获取用户备注信息
	 */
	@ExcelField(title = "备注信息", type = 0, align = 1, sort = 11)
	public String getRemarks() {
		return this.remarks;
	}

}
